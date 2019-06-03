import pymysql

def getCommonPhenotypeFromDB():
    conn = pymysql.connect(host='localhost', user='root', password='', db='omim', charset="utf8")
    cur = conn.cursor()

    sqla = '''SELECT stid FROM type_copy1 WHERE stop=0;'''

    try:
        cur.execute(sqla)
        result=cur.fetchall()

    except:
        print("查询失败")

    type_commonp={}

    for i in range(len(result)):
        stid = result[i][0]
        #f = open('tid_'+str(tid)+'.txt', 'w')
        sqlb = '''select pid from (d_p join p_t_copy1 using (pid)) join type_copy1 using (tid) where (description not like "%normal%" or description like "%abnormal%") and stop=0 and stid='''+str(stid)+''';'''
        try:
            cur.execute(sqlb)
            result2=cur.fetchall()


        except:
            print("查询失败")
        pid = [x[0] for x in result2]
        # print(pid)

        cnt_pid = {}
        for j in pid:
            if j not in cnt_pid:
                cnt_pid[j] = 0
            cnt_pid[j] += 1
        # print(cnt_pid)

        cnt_pid = sorted(cnt_pid.items(), key=lambda x: x[1], reverse=True)[:10]
        common_p = [x[0] for x in cnt_pid]
        # print(tid,common_p)

        if common_p:
            type_commonp[stid] = common_p


        # for j in common_p:
        #     print(j,file=f)
        # f.close()
    print(type_commonp)

    f = open('common_phenotype.txt', 'w')
    for i in type_commonp:
        f.write("\n"+str(i))
        for j in type_commonp[i]:
            f.write("\t"+str(j))

    cur.close()
    conn.close()
    f.close()
    return type_commonp

def getCommonPhenotypeFromFile():
    conn = pymysql.connect(host='localhost', user='root', password='', db='omim', charset="utf8")
    cur = conn.cursor()
    f=open('common_phenotype.txt','r')
    #f2=open('common_phenotype_ch.txt','w')
    lineset=f.readlines()
    commonp={}
    commonp_ch={}

    for i in range(1,len(lineset)):
        line=lineset[i].rstrip("\n").split("\t")
        tid=int(line[0])
        sqla = '''select type_ch from type_copy1 where tid=''' + str(tid) + ''';'''
        try:
            cur.execute(sqla)
            result = cur.fetchall()
        except:
            print("查询失败")
        type_ch=result[0][0]
        #f2.write("\n"+type_ch)
        pid=[int(line[j]) for j in range(1,len(line))]
        #print(disease,disease_type)
        commonp[tid]=pid
        des_ch=[]
        for j in pid:
            sqlb = '''select des_ch from p_t_copy1 where pid='''+str(j)+''';'''
            try:
                cur.execute(sqlb)
                result2 = cur.fetchall()

            except:
                print("查询失败")
            des_ch.append(result2[0][0])

            #f2.write("\t\t\t"+result2[0][0])
            # sqlc = '''insert into common_p(pid,tid) values(%s,%s);'''
            # try:
            #     cur.execute(sqlc, (j,tid))
            #     conn.commit()
            #     print('表common_p插入成功')
            #
            # except:
            #     print("表common_p插入失败")

        commonp_ch[type_ch]=des_ch



    print(commonp)
    print(commonp_ch)
    f.close()
    #f2.close()
    cur.close()
    conn.close()

    return commonp

def diagnose_pid(pinput):
    conn = pymysql.connect(host='localhost', user='root', password='', db='omim', charset="utf8")
    cur = conn.cursor()
    f=open('omim2tcm.txt','r')
    lineset=f.readlines()
    omim_tcm={}

    for i in range(1,len(lineset)):
        line=lineset[i].rstrip("\n").split("\t")
        disease=int(line[0])
        meridian=[int(line[j]) for j in range(1,len(line))]
        #print(disease,disease_type)
        omim_tcm[disease]=meridian
        #omim.append(disease_type)
    #print(omim)


    sqla = '''select mimnumber,pid,des_ch from (d_p join p_t_copy1 using (pid)) join type_copy1 using (tid) where (description not like "%normal%" or description like "%abnormal%") and stop=0;'''
    try:
        cur.execute(sqla)
        result=cur.fetchall()


    except:
        print("查询失败")

    d_p={}

    for i in result:
        #print(i)
        mimnumber=i[0]
        pid=i[1]
        des_ch=i[2]
        if mimnumber not in d_p:
            d_p[mimnumber]=[]
        d_p[mimnumber].append(pid)

    #print(d_p)
    pinput=pinput.split(" ")
    pinputset=[int(x) for x in pinput]
    diagnose_result=[]
    tcm_result={}
    print("你可能患上以下疾病：")
    for i in d_p:
        if set(d_p[i])>=set(pinputset):
            #print(i)
            # sqlb = '''select full_name from mim_disease where mimnumber='''+str(i)+''';'''
            # try:
            #     cur.execute(sqlb)
            #     full_name = cur.fetchall()[0][0]
            #
            # except:
            #     print("查询失败")

            # if i in omim_tcm:
            #     print(i,full_name,"可能出问题的经络:",omim_tcm[i])
            # else:
            #     print(i,full_name,"暂无可能出问题的经络")
            #diagnose_result[i] = full_name
            diagnose_result.append(i)
            if i in omim_tcm:
                tcm_result[i]=omim_tcm[i]
            else:
                tcm_result[i]=[]



    cur.close()
    conn.close()
    print(diagnose_result)
    print(tcm_result)

    return diagnose_result,tcm_result







if __name__ == '__main__':


    #type_commonp=getCommonPhenotypeFromDB()
    type_commonp2 = getCommonPhenotypeFromFile()
    pinput=input("病症（用空格分割）：")
    diagnose_pid(pinput)


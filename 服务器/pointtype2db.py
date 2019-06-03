import pymysql

conn = pymysql.connect(host='localhost', user='root', password='123456', db='chinese_medicine', charset="utf8")
cur = conn.cursor()
for line in open('cluster_result.txt', 'r').readlines():
    line=line.split('\t')
    tid=line[0]
    cluster=line[2]
    cluster=cluster.split("[")[1].split("]")[0].split(", ")
    for i in cluster:

        sqla = '''
        insert into p_test(pid,tid) values(%s,%s);
        '''

        try:
            cur.execute(sqla, (i,tid))
            conn.commit()
            #print('表p_test插入成功')
        except:
            print("表p_test插入失败")


conn.commit()
cur.close()
conn.close()
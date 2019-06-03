#-*-coding:utf-8-*-
from flask import Flask, render_template, Response
from flask import request
import os
import json
import pymysql
import diagnose

app = Flask(__name__)
basedir=os.path.abspath(os.path.dirname(__file__))
app.config['SQLALCHEMY_COMMIT_ON_TEARDOWN']=True
app.config['SQLALCHEMY_TRACK_MODIFICATIONS']=True
pid=0


#testInfo = {}

@app.route('/')
def test():
    return '服务器正常运行'



# #此方法处理用户注册
# @app.route('/register',methods=['GET','POST'])
# def register():
#     user = {}
#     if request.method == 'POST':
#         username = request.form["username"]
#         password = request.form["password"]
#         print(username,password)
#     else:
#
#         user["username"] = request.args["username"]
#         user["password"] = request.args["password"]
#
#
#     return json.dumps(user,ensure_ascii=False)

#登录
@app.route('/login',methods=['GET','POST'])
def login():
    if request.method == 'POST':
        username = request.form["username"]
        password = request.form["password"]
        return "成功"
        #print(username,password)
    else:

        username = request.args["username"]
        password = request.args["password"]
        #uid=0
        print(username,password)
        conn = pymysql.connect(host='localhost', user='root', password='', db='user', charset="utf8")
        cur = conn.cursor()
        sqla = """SELECT uid FROM user WHERE username='""" + username + """' AND password='"""+password+"""';"""
        try:
            cur.execute(sqla)
            userid = cur.fetchall()[0][0]
            print(userid)

        except:
            userid=0
            print(userid)

        cur.close()
        conn.close()

        return str(userid)




#查询
@app.route('/point',methods=['GET','POST'])
def point():
    conn = pymysql.connect(host='localhost', user='root', password='', db='chinese_medicine', charset="utf8")
    cur = conn.cursor()

    sqla = """SELECT pid,pname,mid FROM point JOIN p_m USING (pid);"""
    try:
        cur.execute(sqla)
        result=cur.fetchall()
    except:
        print("查询失败")
    list=[]
    for i in result:
        point = {}
        point["pid"]=i[0]
        point["pname"]=i[1]
        point["mid"]=i[2]
        list.append(point)
    cur.close()
    conn.close()
    return json.dumps(list,ensure_ascii=False)



@app.route('/pinfo',methods=['GET','POST'])
def pinfo():
    pinfo = {}
    if request.method == 'POST':
        pid =request.form["pid"]
        print(pid)

    else:
        pid = request.args["pid"]
        conn = pymysql.connect(host='localhost', user='root', password='', db='chinese_medicine', charset="utf8")
        cur = conn.cursor()
        sqla = """SELECT location,treatment,operation,anatomy FROM p_info WHERE pid=""" + str(pid) + """;"""
        try:
            cur.execute(sqla)
            result = cur.fetchall()

        except:
            print("查询失败")
        pinfo = {}
        pinfo["location"] = result[0][0]
        pinfo["treatment"] = result[0][1]
        pinfo["operation"] = result[0][2]
        pinfo["anatomy"] = result[0][3]
        print(pinfo)

        cur.close()
        conn.close()

    return json.dumps(pinfo,ensure_ascii=False)



@app.route('/meridian',methods=['GET','POST'])
def meridian():
    conn = pymysql.connect(host='localhost', user='root', password='', db='chinese_medicine', charset="utf8")
    cur = conn.cursor()
    sqla = '''SELECT mid,mname FROM meridian;'''
    #sqla = '''SELECT pid,description FROM p_t_copy1 JOIN type_copy1 USING (tid) WHERE stop=0;'''
    try:
        cur.execute(sqla)
        result=cur.fetchall()

    except:
        print("查询失败")

    list=[]
    for i in result:
        meridian = {}
        meridian["mid"]=i[0]
        meridian["mname"]=i[1]
        list.append(meridian)

    cur.close()
    conn.close()

    return json.dumps(list,ensure_ascii=False)


@app.route('/phenotype',methods=['GET','POST'])
def phenotype():

    if request.method == 'POST':
        tid =request.form["tid"]
        print(tid)
        return "成功"

    else:
        tid = request.args["tid"]
        conn = pymysql.connect(host='localhost', user='root', password='', db='omim', charset="utf8")
        cur = conn.cursor()

        sqla = """SELECT pid,des_ch FROM common_p JOIN p_t_copy1 USING (pid) WHERE common_p.tid=""" + str(tid) + """;"""
        try:
            cur.execute(sqla)
            result = cur.fetchall()

        except:
            print("查询失败")
        list = []
        for i in result:
            phenotype = {}
            phenotype["pid"] = i[0]
            phenotype["description"] = i[1]
            list.append(phenotype)

        cur.close()
        conn.close()

        #commonp=diagnose.getCommonPhenotypeFromFile()
        print(list)

        return json.dumps(list,ensure_ascii=False)

@app.route('/diagnose',methods=['GET','POST'])
def diagnosePid():

    if request.method == 'POST':
        if request.method == 'POST':
            pid = request.form["pid"]
            print(pid)
            conn = pymysql.connect(host='localhost', user='root', password='', db='test', charset="utf8")
            cur = conn.cursor()

            sqla = """insert into testinput(pid,user) values(%s,%s);"""
            try:
                cur.execute(sqla, (pid,1))
                conn.commit()

            except:
                print("插入失败")

            cur.close()
            conn.close()
            return "成功"

    else:
        conn = pymysql.connect(host='localhost', user='root', password='', db='omim', charset="utf8")
        cur = conn.cursor()
        conn2 = pymysql.connect(host='localhost', user='root', password='', db='chinese_medicine', charset="utf8")
        cur2 = conn2.cursor()
        conn3 = pymysql.connect(host='localhost', user='root', password='', db='test', charset="utf8")
        cur3 = conn3.cursor()

        user = request.args["user"]

        sqlc = '''select pid from testinput where user='''+str(user)+''';'''
        try:
            cur3.execute(sqlc)
            result3 = cur3.fetchall()
        except:
            print("查询失败")
        input=[str(x[0]) for x in result3]
        input=' '.join(input)
        print(input)
        diagnose_result, tcm_result = diagnose.diagnose_pid(input)
        deseaselist=[]
        desease = []
        meridian = {}
        for i in diagnose_result:
            finalresult = {}
            sqla = '''select full_name from mim_disease where mimnumber=''' + str(i) + ''';'''
            try:
                cur.execute(sqla)
                result = cur.fetchall()
            except:
                print("查询失败")
            #desease.append(result[0][0])
            finalresult["desease"] = result[0][0]
            meridian[i]=[]
            if tcm_result[i]:
                for j in tcm_result[i]:
                    sqlb = '''select mname from meridian where mid=''' + str(j) + ''';'''
                    try:
                        cur2.execute(sqlb)
                        result2 = cur2.fetchall()

                    except:
                        print("查询失败")
                    #meridian[i].append(result2[0][0])
                    finalresult["meridian"] = result2[0][0]
            else:
                finalresult["meridian"] = "老铁没毛病"

            deseaselist.append(finalresult)


        cur.close()
        conn.close()
        cur2.close()
        conn2.close()
        print(deseaselist)

        return json.dumps(deseaselist,ensure_ascii=False)

@app.route('/input', methods=['GET', 'POST'])
def input():

    if request.method == 'POST':
        pid = request.form["pid"]
        print(pid)
        conn = pymysql.connect(host='localhost', user='root', password='', db='test', charset="utf8")
        cur = conn.cursor()

        sqla = """insert into testinput(pid) values(%s);"""
        try:
            cur.execute(sqla, (pid))
            conn.commit()

        except:
            print("插入失败")

        cur.close()
        conn.close()
        return "成功"

    else:
        tid = request.args["tid"]
        conn = pymysql.connect(host='localhost', user='root', password='', db='omim', charset="utf8")
        cur = conn.cursor()

        sqla = """SELECT pid,des_ch FROM common_p JOIN p_t_copy1 USING (pid) WHERE common_p.tid=""" + str(
            tid) + """;"""
        try:
            cur.execute(sqla)
            result = cur.fetchall()

        except:
            print("查询失败")
        list = []
        for i in result:
            phenotype = {}
            phenotype["pid"] = i[0]
            phenotype["description"] = i[1]
            list.append(phenotype)

        cur.close()
        conn.close()

        # commonp=diagnose.getCommonPhenotypeFromFile()
        print(list)

        return json.dumps(list, ensure_ascii=False)

# 查询历史
@app.route('/history',methods=['GET','POST'])
def history():

    pinfo = {}
    if request.method == 'POST':
        pid =request.form["pid"]
        print(pid)

    else:
        hid = request.args["hid"]
        print(hid)
        conn = pymysql.connect(host='localhost', user='root', password='', db='user', charset="utf8")
        cur = conn.cursor()
        sqla = """SELECT * FROM history where hid="""+str(hid)+""";"""
        try:
            cur.execute(sqla)
            result = cur.fetchall()

            #print(result[0][0])
        except:
            print("查询失败")
        list = []
        for i in result:
            history = {}
            history["hid"] = i[0]
            history["date"] = i[1]
            history["uid"] = i[2]
            history["d1"] = i[3]
            history["p1"] = i[4]
            history["m1"] = i[5]
            history["d2"] = i[6]
            history["p2"] = i[7]
            history["m2"] = i[8]
            history["d3"] = i[9]
            history["p3"] = i[10]
            history["m3"] = i[11]
            history["s1"] = i[12]
            history["s2"] = i[13]
            history["s3"] = i[14]
            history["s4"] = i[15]
            history["s5"] = i[16]
            list.append(history)

        cur.close()
        conn.close()

        return json.dumps(list,ensure_ascii=False)


if __name__ == '__main__':
    app.run(host='192.168.137.1')


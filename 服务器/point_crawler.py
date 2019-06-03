import requests

from bs4 import BeautifulSoup
import pymysql
import os
import re
import sys

def getPointDetail(soup):
    content = soup.select("#PanelContent")[0]
    [s.extract() for s in content('p')]
    [s.extract() for s in content('div')]
    content=str(content).replace(r'<br/>',"\n")
    content = BeautifulSoup(content, 'html.parser').text.lstrip()

    #print(content)
    location = re.findall('\【定位\】(.*?)\n', content)
    if len(location)==0:
        location = re.findall('\【位置\】(.*?)\n', content)
        if len(location)==0:
            location=["null"]
    location = re.sub(u"\\（见图.*?\\）", "", location[0])

    treatment = re.findall('\【主治\】(.*?)\n', content)
    if len(treatment)==0:
        treatment = re.findall('\【功效\】(.*?)\n', content)
        if len(treatment)==0:
            treatment=["null"]
    treatment=treatment[0]

    operation = re.findall('\【操作\】(.*?)\n', content)
    if len(operation) == 0:
        operation = ["null"]
    operation=operation[0]

    anatomy = re.findall('\【解剖\】(.*?)\n', content)
    if len(anatomy) == 0:
        anatomy = ["null"]
    anatomy=anatomy[0]

    return location,treatment,operation,anatomy

def meridian2DB(meridian,cur,conn):

    sqla = '''
    insert into meridian(mname) values(%s);
    '''
    try:
        cur.execute(sqla,(meridian))
        conn.commit()
        print('表meridian插入成功')
    except:
        print("表meridian插入失败")

def point2DB(mid,pid,name,location, treatment,operation,anatomy,meridian,cur,conn):

    # sqlc='''
    # CREATE TABLE IF NOT EXISTS point(Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(25))
    # '''
    # try:
    #     cur.execute(sqlc)
    #     conn.commit()
    #     print('建表成功')
    # except:
    #
    #      print("建表失败")

    sqla = '''
    insert into point(pname) values(%s);
    '''
    try:
        cur.execute(sqla,(name))
        conn.commit()
        print('表point插入成功')
    except:
        print("表point插入失败")

    sqlb = '''
    insert into p_m(pid,mid) values(%s,%s);
    '''
    #print(pid,mid)
    try:
        cur.execute(sqlb, (pid, mid))
        conn.commit()
        print('表p_m插入成功')
    except:
        print("表p_m插入失败")

    sqlc = '''
    insert into p_info(pid,location,treatment,operation,anatomy) values(%s,%s,%s,%s,%s);
    '''
    try:
        cur.execute(sqlc, (pid, location, treatment, operation, anatomy))
        conn.commit()
        print('表p_info插入成功')
    except:
        print("表p_info插入失败")

def downloadPic(root,soup,meridian,point):

    picList = soup.select("#PanelContent .imgTable a")
    for i in picList:
        href=i["href"]
        picName=href.split("/")[-1]
        catlog = root + meridian+"/"+point+"/"
        path=catlog+picName

        try:
            if not os.path.exists(catlog):
                os.makedirs(catlog)
            if not os.path.exists(path):
                r = requests.get(href)
                r.raise_for_status()
                # 使用with语句可以不用自己手动关闭已经打开的文件流
                with open(path, "wb") as f:  # 开始写文件，wb代表写二进制文件
                    f.write(r.content)
                print(picName,":爬取完成")
            else:
                print(picName,":文件已存在")
        except Exception as e:
            print(picName,":爬取失败:" + str(e))


def main():
    conn = pymysql.connect(host='localhost', user='root', password='123456', db='chinese_medicine',charset="utf8")
    cur = conn.cursor()
    try:
        res = requests.get("http://www.taozhy.com/ShuJuKu/XueWei/index.aspx?tdsourcetag=s_pcqq_aiomsg")

    except requests.exceptions.RequestException as e:  # This is the correct syntax
        print(e)
        res = requests.get("http://www.taozhy.com/ShuJuKu/XueWei/index.aspx?tdsourcetag=s_pcqq_aiomsg",timeout=30)

        #sys.exit(1)

    soup = BeautifulSoup(res.text, 'html.parser')
    root="E://chinese-medicine/"
    p=0

    for j in range(19):
        meridian = soup.select("#PanelContentMuLu div")[j].text
        meridian = re.search(r'\d+、(.*)', meridian, re.M | re.I).group(1)
        meridian=meridian.rstrip()
        meridian2DB(meridian, cur, conn)

        for i in soup.select("#PanelContentMuLu table")[j].select("a"):
            p+=1
            name=i.text
            print(name)
            url = 'http://www.taozhy.com/ShuJuKu/XueWei/'+i['href']
            try:
                res2 = requests.get(url)
            except requests.exceptions.RequestException as e:  # This is the correct syntax
                print(e)
                res2 = requests.get(url,timeout=30)
                #sys.exit(1)

            soup2 = BeautifulSoup(res2.text, 'html.parser')
            #downloadPic(root, soup2,meridian,name)
            location, treatment, operation, anatomy=getPointDetail(soup2)
            getPointDetail(soup2)
            point2DB(j+1,p,name,location, treatment,operation,anatomy,meridian,cur,conn)
            print("\n")

    conn.commit()
    cur.close()
    conn.close()

main()

import jieba
import pymysql
import jieba.posseg as psg
import jieba.analyse





# def read_from_file(file_name):
#     with open(file_name,"r") as fp:
#         words = fp.read()
#     return words

def read_from_db():
    conn = pymysql.connect(host='localhost', user='root', password='123456', db='chinese_medicine', charset="utf8")
    cur = conn.cursor()
    f = open('fenci2.txt', 'w')  # 若是'wb'就表示写二进制文件
    f2 = open('stopword.txt', 'r')
    stop_words_set=f2.read().split("\n")
    #print(stop_words_set)


    for i in range(10):
        sqla = '''select anatomy from p_info where pid=''' + str(i + 1) + ";"
        try:
            cur.execute(sqla)
            words = cur.fetchall()[0][0]
            print(words)
            words=words.replace(' ','，')
            # print('查询成功')
            # s = u'在肛尾膈中；有肛门动、静脉分支，棘突间静脉丛的延续部；布有尾神经后支及肛门神经。'
            #list = [x.word for x in psg.cut(results) if x.flag.startswith('n')]
            result = jieba.cut(words)
            new_words = ""
            noun_words = []
            for r in result:
                if r not in stop_words_set:
                    new_words=new_words+r+"\t"
                    # if r.flag.startswith('n'):
                    #     noun_words.append(r)
            print(new_words)
            keywords = jieba.analyse.extract_tags(new_words, topK=5, withWeight=True, allowPOS=())
            # 访问提取结果

            for item in keywords:
                # 分别为关键词和相应的权重
                print(item[0], item[1])


            tfidf = jieba.analyse.extract_tags

            # 原始文本
            # 基于TF-IDF算法进行关键词抽取
            keywords2 = tfidf(new_words)
            print("keywords by tfidf:")
            # 输出抽取出的关键词
            for keyword in keywords2:
                print(keyword + "/")

            textrank = jieba.analyse.textrank

            print("\nkeywords by textrank:")
            # 基于TextRank算法进行关键词抽取
            keywords3 = textrank(new_words)
            # 输出抽取出的关键词
            for keyword in keywords3:
                print(keyword + "/")
            #print(noun_words)
            #return set(new_words)
            # print(list)
            # f.write(str(i + 1))
            # for j in list:
            #     f.write("\t" + j)
            # f.write("\n")
        except:
            print("查询失败")

    cur.close()
    conn.close()
    f.close()
    f2.close()

read_from_db()




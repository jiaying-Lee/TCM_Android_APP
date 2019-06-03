import jieba
import pymysql
import jieba.posseg as psg
import jieba.analyse
import os
import numpy as np
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.manifold import TSNE
from sklearn import feature_extraction
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer


from sklearn import metrics

def read_from_db():
    conn = pymysql.connect(host='localhost', user='root', password='123456', db='chinese_medicine', charset="utf8")
    cur = conn.cursor()
    f = open('fenciAll.txt', 'w')  # 若是'wb'就表示写二进制文件
    f2 = open('stopword.txt', 'r')
    stop_words_set=f2.read().split("\n")

    for i in range(501):
        sqla = '''select anatomy from p_info where pid=''' + str(i + 1) + ";"
        try:
            cur.execute(sqla)
            words = cur.fetchall()[0][0]
            words=words.replace(' ','，')
            jieba.load_userdict("coal_dict.txt")
            result = jieba.cut(words)

            #a=[x.word for x in psg.cut(result) if x.flag.startswith('n')]
            new_words=[]
            for r in result:
                if r not in stop_words_set:
                    new_words.append(r)
            if len(new_words)==0:
                new_words.append("null")
            f.write(' '.join(new_words) + "\n")


            #print(noun_words)
            #return set(new_words)
            # print(list)
            # f.write(str(i + 1))
            # for j in list:
            #     f.write("\t" + j)
            # f.write("\n")
            #print("/".join(result))
        except:
            print("查询失败")
    #print(new_words)
    cur.close()
    conn.close()
    f.close()
    f2.close()
    #print(new_words)
    return new_words



# def get_all_vector(new_words):
#     docs = []
#     word_set = set()
#     for i in range(501):
#         if i not in new_words.keys():
#             new_words[i]=["未知"]
#         doc=new_words[i]
#         #print(doc)
#         docs.append(doc)
#         word_set |= set(doc)
#     #print(word_set)
#     word_set = list(word_set)
#     docs_vsm = []
#     #for word in word_set[:30]:
#         #print word.encode("utf-8"),
#     for doc in docs:
#         temp_vector = []
#         for word in word_set:
#             temp_vector.append(doc.count(word) * 1.0)
#         #print temp_vector[-30:-1]
#         docs_vsm.append(temp_vector)
#
#     docs_matrix = np.array(docs_vsm)
#     return docs_matrix

# def tfidf(docs_matrix):
#     column_sum = [ float(len(np.nonzero(docs_matrix[:,i])[0])) for i in range(docs_matrix.shape[1]) ]
#     column_sum = np.array(column_sum)
#     column_sum = docs_matrix.shape[0] / column_sum
#     idf =  np.log(column_sum)
#     idf =  np.diag(idf)
#     # 请仔细想想，根绝IDF的定义，计算词的IDF并不依赖于某个文档，所以我们提前计算好。
#     # 注意一下计算都是矩阵运算，不是单个变量的运算。
#     for doc_v in docs_matrix:
#         if doc_v.sum() == 0:
#             doc_v = doc_v / 1
#         else:
#             doc_v = doc_v / (doc_v.sum())
#         tfidf = np.dot(docs_matrix,idf)
#         return tfidf
#
# def gen_sim(A,B):
#     num = float(np.dot(A,B.T))
#     denum = np.linalg.norm(A) * np.linalg.norm(B)
#     if denum == 0:
#         denum = 1
#     cosn = num / denum
#     sim = 0.5 + 0.5 * cosn
#     return sim
#
# def randCent(dataSet, k):
#     n = np.shape(dataSet)[1]    #词的总个数
#     centroids = np.mat(np.zeros((k,n)))#create centroid mat
#     for j in range(n):#create random cluster centers, within bounds of each dimension
#         minJ = min(dataSet[:,j])
#         rangeJ = float(max(dataSet[:,j]) - minJ)
#         centroids[:,j] = np.mat(minJ + rangeJ * np.random.rand(k,1))
#     return centroids




new_words=read_from_db()
# docs_matrix=get_all_vector(new_words)
#
# dataSet=tfidf(docs_matrix)


#kMeans(dataSet, 10, distMeas=gen_sim, createCent=randCent)
# k=[5,10,20,30,40,50,60,70,80,90]
# for i in k:
#     kmeans=KMeans(n_clusters=i).fit(dataSet)
#     # a=b.labels_
#     # print(a)
#     #print(i,":",b.inertia_)
#     score = metrics.silhouette_score(dataSet, kmeans.labels_, metric='euclidean')
#     print(i,":",score)
#
# cluster={}

# for i in range(k):
#     cluster[i]=[]
# for i in range(len(a)):
#     for j in range(k):
#         if a[i]==j:
#             cluster[j].append(i+1)
# for i in range(k):
#     print(i,":",cluster[i])
#print(len(a))


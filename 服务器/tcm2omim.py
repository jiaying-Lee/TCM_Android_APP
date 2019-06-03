import pymysql
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer
import numpy as np
from scipy.sparse import csr_matrix
#from gensim import corpora,models,similarities

def omim_disease2type():
    conn = pymysql.connect(host='localhost', user='root', password='', db='omim', charset="utf8")
    cur = conn.cursor()

    sqla = '''select mimnumber,tid from (d_p join p_t using (pid)) join type_copy1 using (tid) where (description not like "%normal%" or description like "%abnormal%") and stop=0;'''
    try:
        cur.execute(sqla)
        words = cur.fetchall()
        #print(len(words2))

    except:
        print("查询失败")
    words2 = []
    # print(words)
    a = 0
    f = open('omim.txt', 'w')  # 若是'wb'就表示写二进制文件

    for i in range(len(words)):
        j = words[i]
        if j not in words2:
            words2.append(words[i])
            if j[0] != a:
                a = j[0]
                f.write("\n" + str(a) + "\t" + str(j[1]))
            else:
                f.write("\t" + str(j[1]))

    cur.close()
    conn.close()
    f.close()


def tcm_meridian2type():
    f1=open('tcm_orig.txt', 'r')
    meridian=f1.readlines()
    f2 = open('tcm.txt', 'w')
    for i in range(len(meridian)):
        type=[int(j) for j in meridian[i].rstrip("\n").split(" ")]
        type=sorted(type)
        #print(type)
        f2.write("\n" + str(i+1))
        for j in type:
            f2.write("\t"+str(j))
    f1.close()
    f2.close()

def tcm2omim():
    f1=open('omim.txt','r')
    f2 = open('tcm.txt', 'r')
    lineset1=f1.readlines()
    lineset2=f2.readlines()
    omim={}
    #omim=[]
    for i in range(1,len(lineset1)):
        line1=lineset1[i].rstrip("\n").split("\t")
        disease=int(line1[0])
        disease_type=[int(line1[j]) for j in range(1,len(line1))]
        #print(disease,disease_type)
        omim[disease]=disease_type
        #omim.append(disease_type)
    #print(omim)

    tcm={}
    #tcm=[]
    tcm_subset={}

    for i in range(1,len(lineset2)):
        line2=lineset2[i].rstrip("\n").split("\t")
        meridian=int(line2[0])
        meridian_type=[int(line2[j]) for j in range(1,len(line2))]
        #print(disease,disease_type)
        tcm[meridian]=meridian_type
        #tcm_subset[meridian]=getSubSets(meridian_type)

    omim_tcm={}
    # for i in omim:
    #     for j in tcm_subset:
    #         subset=tcm_subset[j]
    #         for k in subset:
    #             if k:
    #                 if omim[i]==k:
    #                     if i not in omim_tcm:
    #                         omim_tcm[i]=[]
    #                     match=(j,len(subset))
    #                     omim_tcm[i].append(match)
    for i in omim:
        for j in tcm:
            if set(omim[i])<=set(tcm[j]):
                if i not in omim_tcm:
                    omim_tcm[i] = []
                omim_tcm[i].append(j)
    print(omim_tcm)
    f3 = open('omim2tcm.txt', 'w')
    for i in omim_tcm:
        f3.write("\n"+str(i))
        for j in omim_tcm[i]:
            f3.write("\t"+str(j))
    # for i in omim_tcm:
    #     disease2meridian=omim_tcm[i]
    #     #disease2meridian.sort(key=lambda x: x[1],reverse=True)
    #     maxmatch = max([x[1] for x in disease2meridian])
    #     result = []
    #     for j in disease2meridian:
    #         if j[1] == maxmatch:
    #             result.append(j[0])
    #     print(i,result,file=f3)
    f1.close()
    f2.close()
    f3.close()


    # dictionary = corpora.Dictionary(omim)
    # corpus = [dictionary.doc2bow(doc) for doc in omim]
    # doc_test_vec = dictionary.doc2bow(tcm[2])
    # tfidf = models.TfidfModel(corpus)
    # #print(tfidf[doc_test_vec])
    # index = similarities.SparseMatrixSimilarity(tfidf[corpus], num_features=len(dictionary.keys()))
    # sim = index[tfidf[doc_test_vec]]
    # omimsim=sorted(enumerate(sim), key=lambda item: -item[1])
    # sim_index=omimsim[0][0]
    # print(sim_index,omim[sim_index])

    # for i in tcm:
    #     for j in omim:
    #         if omim[j]==tcm[i]:
    #             print(i,j)

    # tcm_array=np.zeros((len(tcm),73),dtype=np.int)
    # for i in tcm:
    #     for j in tcm[i]:
    #         tcm_array[i-1][j-1]+=1

    # omim_array=np.zeros(73,dtype=np.int)
    # for i in omim:
    #     for j in omim[i]:
    #         omim_array[j-1]+=1
    # print(omim_array)
    # print(omim_array[4],omim_array[13],omim_array[16],omim_array[21],omim_array[26],omim_array[33],omim_array[41],omim_array[48],omim_array[54],omim_array[55])

    # tcm_cntvector=csr_matrix(tcm_array)
    # #print(tcm_cntvector)
    #
    # transformer = TfidfTransformer()
    # tfidf = transformer.fit_transform(tcm_cntvector)
    # #print(tfidf)

def ptype2stype():
    conn = pymysql.connect(host='localhost', user='root', password='', db='omim', charset="utf8")
    cur = conn.cursor()

    sqla = '''select tid,stid from type_copy1 where stop=0;'''
    try:
        cur.execute(sqla)
        type = cur.fetchall()

    except:
        print("查询失败")
    type2 = {}

    for i in type:
        ptype=i[1]
        stype=i[0]
        if stype==ptype:
            type2[ptype]=[]
        else:
            type2[ptype].append(stype)


    cur.close()
    conn.close()
    print(type2)

def getSubSets(items):
    # the power set of the empty set has one element, the empty set
    result = [[]]
    for x in items:
        result.extend([subset + [x] for subset in result])
    return result

if __name__ == "__main__":
    #omim_disease2type()
    #tcm_meridian2type()
    #ptype2stype()
    tcm2omim()
    #test()
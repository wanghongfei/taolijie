# encoding=UTF-8
__author__ = 'whf'

import jieba

'''
关键字生成器.
'''
class KeywordGenerator:
    def __init__(self, file_path):
        self.stop_words = []
        self.file_path = file_path
        self.__read_word__()

    '''
    生成关键字
    '''
    def gen_keywords(self, text):
        result_list = list(jieba.cut_for_search(text))
        return self.__trim_stop_word__(result_list)

    # 清空stop-word表
    def clear_stop_words(self):
        self.stop_words = []

    # 从文件中重新读取stop-word表
    # file_path: 文件路径
    def reload_stop_words(self, file_path):
        self.file_path = file_path
        self.__read_word__()

    '''
    逐行从文件中读取stop words
    '''
    def __read_word__(self):
        for line in open(self.file_path):
            self.stop_words.append(line.strip('\n'))

    # 从key_list中删除stop-word
    def __trim_stop_word__(self, key_list):
        # 记录下标点的下标
        index = 0
        index_list = []
        for key in key_list:
            print 'test trim? = ' + key
            if key in self.stop_words:
                print 'trim added'
                index_list.append(index)
            index += 1
        # 删除标点
        for ix in index_list:
            key_list.remove(ix)
        return key_list

    '''
    返回stop word列表
    '''
    def __get_stop_words__(self):
        return self.stop_words


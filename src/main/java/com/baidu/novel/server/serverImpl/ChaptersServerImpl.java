package com.baidu.novel.server.serverImpl;

import com.baidu.novel.common.Constant;
import com.baidu.novel.entity.Chapters;
import com.baidu.novel.entity.Comment;
import com.baidu.novel.exception.ServiceException;
import com.baidu.novel.mapper.BookshelfMapper;
import com.baidu.novel.mapper.ChaptersMapper;
import com.baidu.novel.mapper.CommentMapper;
import com.baidu.novel.mapper.NovelMapper;
import com.baidu.novel.server.ChaptersServer;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;


@Service
public class ChaptersServerImpl extends ServiceImpl<ChaptersMapper, Chapters> implements ChaptersServer {
    @Resource
    private ChaptersMapper chaptersMapper;
    @Resource
    private NovelMapper novelMapper;
    @Resource
    CommentMapper commentMapper;
    @Resource
    BookshelfMapper bookshelfMapper;
    @Override
    public Page<Chapters> Finall(Page<Chapters> page,String bname) {
        return chaptersMapper.Finall(page,bname);
    }

    @Override
    public boolean insert(Chapters chapters, Integer bid) {
        if (chapters.getId() == null ) {
                chapters.setBId(bid);
                return  save(chapters);
        }else{
            return false;
        }

    }

    @Override
    public List<Chapters> catalogue(Integer id) {
        List<Chapters> chaptersList=chaptersMapper.catalogue(id);
        for (int i = 0; i < chaptersList.size()-1;i++ ){
            for(int j =0; j < chaptersList.size()-1-i;j++){
                //取出对象
                Chapters chapters =chaptersList.get(j);
                Chapters ch =  chaptersList.get(j+1);
                if(chapters.getSequence() > ch.getSequence()){
                    chaptersList.set(j,ch);
                    chaptersList.set(j+1,chapters);
                }
            }
        }

//        for(int i=0;i<chaptersList.size()-1;i++){
//            for(int j=0;j<chaptersList.size()-i-1;j++){
//                if(chaptersList.get(j).compareTo(chaptersList.get(j+1))>0){
//                    Chapters chapters = chaptersList.get(j);
//                    chaptersList.set(j,chaptersList.get(j+1));
//                    chaptersList.set(j+1,chapters);
//                    System.out.printf(String.valueOf(chaptersList));
//                }
//            }
//        }

            return chaptersList;
    }

    @Override
    public Chapters reads(Integer bid, Integer sequence) {
        try {
            Chapters c=chaptersMapper.reads(bid,sequence);
            if (c!=null){
                String cha=c.getContent();
                c.setContent(cha.replace("&nbsp;","\n"));
                return c;
            }else{
                return null;
            }

        }catch (ServiceException se){
            throw new ServiceException(Constant.CODE_500,"系统错误");
        }
    }

    @Override
    public boolean updates(Chapters chapters) {
        if (chapters.getId()!=null){
            return updateById(chapters);
        }
        return false;
    }

    @Override
    public Integer delebid(Integer bid) {
        QueryWrapper<Comment> wa = new QueryWrapper<>();
        wa.eq("tableid",bid);
        commentMapper.delete(wa);
        novelMapper.deleteById(bid);
        bookshelfMapper.bok(bid);
        List<Chapters> list=chaptersMapper.selectbid(bid);
        if (list.size() >0){
            chaptersMapper.deleteBatchIds(list);
        }
        return list.size();
    }

    @Override
    public Boolean authorads(String tablename,Chapters chapters) {

      try {
          Integer tableid=  novelMapper.getid(tablename);
          if(tableid!=null){
              QueryWrapper<Chapters> queryWrapper = new QueryWrapper<>();
              queryWrapper.eq("sequence",chapters.getSequence());
              queryWrapper.eq("B_id",tableid);
              Chapters chapters1=getOne(queryWrapper);
              if (chapters1==null){
                  chapters.setBId(tableid);
                  return save(chapters);
              }else {
                  return false;
              }
          }else {
              throw new ServiceException(Constant.CODE_600,"该书籍不存在");
          }
      }catch (Exception e){
          e.printStackTrace();
      }
        return null;
    }
}

package com.baidu.novel.server.serverImpl;

import com.baidu.novel.common.Constant;
import com.baidu.novel.entity.BookShelf;
import com.baidu.novel.entity.Novel;
import com.baidu.novel.exception.ServiceException;
import com.baidu.novel.mapper.BookshelfMapper;
import com.baidu.novel.mapper.NovelMapper;
import com.baidu.novel.mapper.UserMapper;
import com.baidu.novel.server.Novelserver;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class NovelServerImpl extends ServiceImpl<NovelMapper, Novel> implements Novelserver {

    @Resource
    private NovelMapper novelMapper;
    @Resource
    private BookshelfMapper bookshelfMapper;
    @Resource
    private UserMapper userMapper;
    @Override
    public Boolean add(Novel novel) {
        if (novel.getId() ==null){
            return save(novel);
        }else{
            return updateById(novel);
        }

    }

    @Override
    public List<Novel> Popular() {
        return novelMapper.Popular();
    }

    @Override
    public List<Novel> bookshelf(String username) {
        try {
            Integer userid=userMapper.getusid(username);
            List<Integer> tableid= bookshelfMapper.getableid(userid);
            if (tableid.size()>0){
                List<Novel> novels=new ArrayList<>();
                for(Integer tid:tableid){
                    Novel novel=getById(tid);
                    novels.add(novel);
                }
                return novels;
            }else{
                throw  new ServiceException("600","书架没有小说");
            }
        }catch (Exception ex){
                ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean debooksh(String username,Integer tableid) {
        try {
            Integer userid=userMapper.getusid(username);
            if (bookshelfMapper.bookde(userid,tableid)){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addbooksh(String username, Integer tableid) {
        try{

            Integer userid=userMapper.getusid(username);
            if (userid!=null){
                Integer books=bookshelfMapper.getusta(userid,tableid);
                if (books==null){
                    BookShelf bookShelf = new BookShelf();
                    bookShelf.setUserid(userid);
                    bookShelf.setTableid(tableid);
                    return bookshelfMapper.insert(bookShelf);
                }else{
                    return -1;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Boolean releasenovel(String username, Novel novel) {
        try {
            Integer userid=userMapper.getusid(username);
            if (userid!=null){
                QueryWrapper<Novel> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("b_name",novel.getBName());
                Novel novel1=getOne(queryWrapper);
                if (novel1==null){
                    novel.setUId(userid);
                    return save(novel);
                }else{
                    throw  new ServiceException(Constant.CODE_500,"改小说已存在");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Novel> getUN(String username) {
        try {
            Integer userid=userMapper.getusid(username);
            if (userid!=null){
                List<Novel> novels=novelMapper.getun(userid);
                if (novels.size()!=0){
                    return novels;
                }else{
                    return null;
                }
            }
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public List<Novel> Popularfantasy() {
        String btype="玄幻";
        return novelMapper.Popularfantasy(btype);
    }

    @Override
    public List<Novel> Populasciencefiction() {
        String btype="科幻";
        return novelMapper.Popularfantasy(btype);
    }

    @Override
    public List<Novel> Populaxianxia() {
        String btype="仙侠";
        return  novelMapper.Popularfantasy(btype);
    }
}

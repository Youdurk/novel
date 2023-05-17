package com.baidu.novel.server.serverImpl;

import cn.hutool.core.collection.CollUtil;
import com.baidu.novel.entity.Distribute;
import com.baidu.novel.entity.Muen;
import com.baidu.novel.mapper.DistributeMapper;
import com.baidu.novel.mapper.MuenMapper;
import com.baidu.novel.mapper.RoleMapper;
import com.baidu.novel.server.MuenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MuenServiceImpl extends ServiceImpl<MuenMapper, Muen> implements MuenService {

    @Resource
    MuenMapper muenMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    DistributeMapper distributeMapper;
    @Override
    public boolean menuinsert(Muen muen) {
        return saveOrUpdate(muen);
    }

    @Override
    public List<Muen> allmmenu() {
        List<Muen> muens=list();
        List<Muen> muenids=muens.stream().filter(m1 ->m1.getPid()==null).collect(Collectors.toList());
        for (Muen muen: muenids) {
           muen.setChildren(muens.stream().filter(muen1 -> muen.getId().equals(muen1.getPid())).collect(Collectors.toList()));
        }
        return muenids;
    }

    @Override
    public List<Integer> getmuenid(Integer roleid) {
        return muenMapper.getmuenid(roleid);
    }

    @Override
    public List<Muen> getrolemuen(Integer roleid) {
        List<Integer> muenids=roleMapper.selectmuenid(roleid);
        List<Muen> muens=allmmenu();
        List<Muen> list = new ArrayList<>();
        for (Muen muen:muens){
            if(muenids.contains(muen.getId())){
                list.add(muen);
            }
            List<Muen> children=muen.getChildren();
            children.removeIf(child ->!muenids.contains(child.getId()) );
        }
        return list;
    }

    @Override
    public void serrolemuen(Integer roleid, List<Integer> muenids) {
        //先删除绑定的的关系
       distributeMapper.deletroleid(roleid);
        //获取传过来的muenid，然后与roleid绑定
        List<Integer> rolemuen= CollUtil.newArrayList(muenids);
        for (Integer muenid:muenids){
            Muen muen=getById(muenid);
            if (muen.getPid()!=null && !rolemuen.contains(muen.getPid())){
                Distribute distribute=new Distribute();
                distribute.setRoleid(roleid);
                distribute.setMuenid(muen.getPid());
                rolemuen.add(muen.getPid());
            }
            Distribute rolem=new Distribute();
            rolem.setRoleid(roleid);
            rolem.setMuenid(muenid);
           distributeMapper.insert(rolem);
        }

    }
}

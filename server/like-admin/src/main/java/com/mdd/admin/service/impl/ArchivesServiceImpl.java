package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.service.IArchivesService;
import com.mdd.admin.validate.ArchivesCreateValidate;
import com.mdd.admin.validate.ArchivesUpdateValidate;
import com.mdd.admin.validate.ArchivesSearchValidate;
import com.mdd.admin.vo.ArchivesListedVo;
import com.mdd.admin.vo.ArchivesDetailVo;
import com.mdd.common.config.GlobalConfig;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.Archives;
import com.mdd.common.mapper.ArchivesMapper;
import com.mdd.common.util.ListUtils;
import com.mdd.common.util.TimeUtils;
import com.mdd.common.util.UrlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * 党员档案实现类
 * @author LikeAdmin
 */
@Service
public class ArchivesServiceImpl implements IArchivesService {
        
    @Resource
    ArchivesMapper archivesMapper;

    /**
     * 党员档案列表
     *
     * @author LikeAdmin
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ArchivesListedVo>
     */
    @Override
    public PageResult<ArchivesListedVo> list(PageValidate pageValidate, ArchivesSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        MPJQueryWrapper<Archives> archivesMPJQueryWrapper = new MPJQueryWrapper<>();
        archivesMPJQueryWrapper
                .selectAll(Archives.class)
                .select("U.username","U.sn as userSn","D.name as d_name")
                .leftJoin("?_user U ON t.user_id = U.id".replace("?_",GlobalConfig.tablePrefix))
                .leftJoin("?_system_auth_dept D ON t.department = D.id".replace("?_",GlobalConfig.tablePrefix))
                .orderByDesc("id");

        archivesMapper.setSearch(archivesMPJQueryWrapper, searchValidate, new String[]{
            "like:name@t.name:str",
            "=:idcard@t.idcard:str",
            "=:gender@t.gender:int",
            "=:department@t.department:int",
            "=:contact@t.contact:str",
            "=:isMerry@t.is_merry:int",
            "=:joinTime@t.join_time:int",
            "=:realTime@t.real_time:int",
            "=:see@t.see:str",
            "=:situation@t.situation:str",
        });

        IPage<ArchivesListedVo> iPage = archivesMapper.selectJoinPage(new Page<>(page, limit), ArchivesListedVo.class,archivesMPJQueryWrapper);

        List<ArchivesListedVo> list = new LinkedList<>();
        for(ArchivesListedVo item : iPage.getRecords()) {
            ArchivesListedVo vo = new ArchivesListedVo();
            BeanUtils.copyProperties(item, vo);
            vo.setCreateTime(TimeUtils.timestampToDate(item.getCreateTime()));
            vo.setJoinTime(TimeUtils.timestampToDate(item.getJoinTime()));
            vo.setRealTime(TimeUtils.timestampToDate(item.getRealTime()));
            list.add(vo);
        }

        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * 党员档案详情
     *
     * @author LikeAdmin
     * @param id 主键参数
     * @return Archives
     */
    @Override
    public ArchivesDetailVo detail(Integer id) {
        Archives model = archivesMapper.selectOne(
                new QueryWrapper<Archives>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在");

        ArchivesDetailVo vo = new ArchivesDetailVo();
        BeanUtils.copyProperties(model, vo);
        vo.setBirthdayTime(TimeUtils.timestampToDate(model.getBirthdayTime()));
        vo.setJoinTime(TimeUtils.timestampToDate(model.getJoinTime()));
        vo.setRealTime(TimeUtils.timestampToDate(model.getRealTime()));
        return vo;
    }

    /**
     * 党员档案新增
     *
     * @author LikeAdmin
     * @param createValidate 参数
     */
    @Override
    public void add(ArchivesCreateValidate createValidate) {
        Archives model = new Archives();
        model.setUserId(createValidate.getUserId());
        model.setName(createValidate.getName());
        model.setIdcard(createValidate.getIdcard());
        model.setGender(createValidate.getGender());
        model.setBirthdayTime(TimeUtils.dateToTimestamp(createValidate.getBirthdayTime()));
        model.setNation(createValidate.getNation());
        model.setNativePlace(createValidate.getNativePlace());
        model.setAddress(createValidate.getAddress());
        model.setEducation(createValidate.getEducation());
        model.setCollege(createValidate.getCollege());
        model.setArchivesStatus(createValidate.getArchivesStatus());
        model.setDepartment(createValidate.getDepartment());
        model.setPartyFee(createValidate.getPartyFee());
        model.setContact(createValidate.getContact());
        model.setIsFlow(createValidate.getIsFlow());
        model.setIsMerry(createValidate.getIsMerry());
        model.setJoinTime(TimeUtils.dateToTimestamp(createValidate.getJoinTime()));
        model.setRealTime(TimeUtils.dateToTimestamp(createValidate.getRealTime()));
        model.setSee(createValidate.getSee());
        model.setSituation(createValidate.getSituation());
        model.setCreateTime(System.currentTimeMillis() / 1000);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        archivesMapper.insert(model);
    }

    /**
     * 党员档案编辑
     *
     * @author LikeAdmin
     * @param updateValidate 参数
     */
    @Override
    public void edit(ArchivesUpdateValidate updateValidate) {
        Archives model = archivesMapper.selectOne(
                new QueryWrapper<Archives>()
                    .eq("id",  updateValidate.getId())
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        model.setId(updateValidate.getId());
        model.setUserId(updateValidate.getUserId());
        model.setName(updateValidate.getName());
        model.setIdcard(updateValidate.getIdcard());
        model.setGender(updateValidate.getGender());
        model.setBirthdayTime(TimeUtils.dateToTimestamp(updateValidate.getBirthdayTime()));
        model.setNation(updateValidate.getNation());
        model.setNativePlace(updateValidate.getNativePlace());
        model.setAddress(updateValidate.getAddress());
        model.setEducation(updateValidate.getEducation());
        model.setCollege(updateValidate.getCollege());
        model.setArchivesStatus(updateValidate.getArchivesStatus());
        model.setDepartment(updateValidate.getDepartment());
        model.setPartyFee(updateValidate.getPartyFee());
        model.setContact(updateValidate.getContact());
        model.setIsFlow(updateValidate.getIsFlow());
        model.setIsMerry(updateValidate.getIsMerry());
        model.setJoinTime(TimeUtils.dateToTimestamp(updateValidate.getJoinTime()));
        model.setRealTime(TimeUtils.dateToTimestamp(updateValidate.getRealTime()));
        model.setSee(updateValidate.getSee());
        model.setSituation(updateValidate.getSituation());
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        archivesMapper.updateById(model);
    }

    /**
     * 党员档案删除
     *
     * @author LikeAdmin
     * @param id 主键ID
     */
    @Override
    public void del(Integer id) {
        Archives model = archivesMapper.selectOne(
                new QueryWrapper<Archives>()
                    .eq("id", id)
                    .last("limit 1"));

        Assert.notNull(model, "数据不存在!");

        archivesMapper.delete(new QueryWrapper<Archives>().eq("id", id));
    }

}

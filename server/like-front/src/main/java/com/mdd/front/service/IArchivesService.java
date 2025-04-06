package com.mdd.front.service;

import com.mdd.front.vo.ArchivesDetailVo;

public interface IArchivesService {
    ArchivesDetailVo detailForUser(Integer userId);
}

package com.jpacourse.service.impl;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {
    private final VisitDao visitDao;

    @Autowired
    public VisitServiceImpl(VisitDao dao)
    {
        visitDao = dao;
    }

    @Override
    public List<VisitTO> getVisitByPatientId(Long id)
    {
        return visitDao.findAll().stream()
                .filter(x -> Objects.equals(x.getPatientEntity().getId(), id))
                .map(VisitMapper::ToMapper)
                .collect(Collectors.toList());
    }
}

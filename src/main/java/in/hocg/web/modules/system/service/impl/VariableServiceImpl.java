package in.hocg.web.modules.system.service.impl;

import in.hocg.web.lang.CheckError;
import in.hocg.web.modules.system.domain.Variable;
import in.hocg.web.modules.system.domain.repository.VariableRepository;
import in.hocg.web.modules.system.filter.VariableFilter;
import in.hocg.web.modules.system.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.datatables.mapping.DataTablesInput;
import org.springframework.data.mongodb.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Created by hocgin on 2017/11/6.
 * email: hocgin@gmail.com
 */
@Service
public class VariableServiceImpl implements VariableService {
    
    private VariableRepository variableRepository;
    
    @Autowired
    public VariableServiceImpl(VariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }
    
    @Override
    public DataTablesOutput data(DataTablesInput input) {
        return variableRepository.findAll(input);
    }
    
    @Override
    public Variable insert(VariableFilter filter,
                           CheckError checkError) {
        Variable variable = filter.get();
        if (variableRepository.countAllByKey(variable.getKey()) > 0) {
            checkError.putError("字段名已经存在");
            return variable;
        }
        return variableRepository.insert(variable);
    }
    
    @Override
    public Variable update(VariableFilter filter,
                           CheckError checkError) {
        Variable variable = variableRepository.findVariableByIdAndKey(filter.getId(), filter.getKey());
        if (ObjectUtils.isEmpty(variable)) {
            checkError.putError("该系统变量不存在");
            return null;
        }
        
        return variableRepository.save(filter.update(variable));
    }
    
    @Override
    public void delete(CheckError checkError, String... id) {
        for (Variable variable : variableRepository.findAllByIdIn(id)) {
            if (variable.getBuiltIn()) {
                checkError.putError("删除失败, 含有内置对象");
                return;
            }
        }
        variableRepository.deleteAllByIdIn(id);
    }
    
    @Override
    public Variable findById(String id) {
        return variableRepository.findOne(id);
    }
    
    @Override
    public String getValue(String key, String def) {
        Variable variable = variableRepository.findFirstByKey(key);
        if (ObjectUtils.isEmpty(variable)) {
            return def;
        }
        return variable.getValue();
    }
    
    @Override
    public boolean getBool(String key, Boolean def) {
        return Boolean.TRUE.toString().equals(getValue(key, def.toString()));
    }
    
    
}

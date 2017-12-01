package in.hocg.web.modules.system.domain.repository.impl;

import in.hocg.web.modules.base.BaseMongoCustom;
import in.hocg.web.modules.system.domain.Member;
import in.hocg.web.modules.system.domain.repository.custom.MemberRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by hocgin on 2017/11/25.
 * email: hocgin@gmail.com
 */
public class MemberRepositoryImpl
        extends BaseMongoCustom<Member, String>
        implements MemberRepositoryCustom {
    @Override
    public Member findByEmailAvailableTrue(String email) {
        Query query = Query.query(Criteria.where("email").is(email)
                .and("available").is(true));
        return findOne(query);
    }
    
    @Override
    public Member findByEmail(String email) {
        Query query = Query.query(Criteria.where("email").is(email));
        return findOne(query);
    }
    
    @Override
    public Member findOneByToken(String token) {
        Query query = Query.query(Criteria.where("token.$token").is(token));
        return findOne(query);
    }
    
    @Override
    public void resumeToken() {
        Query query = Query.query(Criteria.where("token").ne(null));
        updateMulti(query, Update.update("token.$.count", 0));
    }
    
    @Override
    public List<Member> findAllByDepartmentAndRole(String department, String role) {
        Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(department)) {
            criteria.andOperator(Criteria.where("department.$id").is(new ObjectId(department)));
        }
        if (!StringUtils.isEmpty(role)) {
            criteria.andOperator(Criteria.where("role.$id").is(new ObjectId(role)));
        }
        return find(Query.query(criteria));
    }
}

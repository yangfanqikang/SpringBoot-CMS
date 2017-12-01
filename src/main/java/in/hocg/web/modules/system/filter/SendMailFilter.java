package in.hocg.web.modules.system.filter;

import in.hocg.web.modules.base.filter.BaseFilter;
import in.hocg.web.modules.base.filter.group.Insert;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by hocgin on 2017/12/1.
 * email: hocgin@gmail.com
 */
@Data
public class SendMailFilter extends BaseFilter {
    @NotBlank(message = "目标为必填", groups = {Insert.class})
    private String target; // 0 前台 1 为后台
    private String[] ids; // 接收者
    
    
    public boolean isWeb() {
        return "0".equals(target);
    }
    
    public boolean isAdmin() {
        return "1".equals(target);
    }
}

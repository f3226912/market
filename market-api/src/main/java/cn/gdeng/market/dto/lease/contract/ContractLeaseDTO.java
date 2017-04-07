package cn.gdeng.market.dto.lease.contract;

import cn.gdeng.market.entity.lease.contract.ContractLeaseEntity;
import cn.gdeng.market.enums.BillingUnitEnum;

public class ContractLeaseDTO extends ContractLeaseEntity implements java.io.Serializable{

	private static final long serialVersionUID = 4580586614453557354L;
	 
    public String getBillingUnitStr() {
    	return BillingUnitEnum.getNameByCode(getBillingUnit());
    }
    
}

package cn.gdeng.market.enums;

public interface ExpentitureConstant {

	/** 周期性费项 */
	int EXP_TYPE_CYCLE = 1;
	/** 走表类费项 */
	int EXP_TYPE_METER = 2;
	/** 一次性费项 */
	int EXP_TYPE_DISPOSABLE = 3;
	/** 临时性费项 */
	int EXP_TYPE_TMEP = 4;
	
	/** 指定金额 */
	int RENT_MODE_AMOUNT = 1;
	/** 手工录入 */
	int RENT_MODE_MANUAL = 2;
	/** 按建筑面积 */
	int RENT_MODE_BUILDING_AREA = 3;
	/** 按套内面积 */
	int RENT_MODE_SET_AREA = 4;
	/** 按可出租面积 */
	int RENT_MODE_RENT_AREA = 5;
	
	/** 分段收费 */
	int SECTIONAL_CHARGE = 1;
	/** 非分段收费 */
	int UN_SECTIONAL_CHARGE = 0;
}

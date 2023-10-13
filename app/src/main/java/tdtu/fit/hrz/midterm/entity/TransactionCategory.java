package tdtu.fit.hrz.midterm.entity;

import tdtu.fit.hrz.midterm.R;
public enum TransactionCategory {
    GENERAL(R.drawable.ic_cate_general),
    FOOD(R.drawable.ic_cate_food),
    CAFE(R.drawable.ic_cate_coffee),
    TRANSPORTATION(R.drawable.ic_cate_transportation),
    GAS(R.drawable.ic_cate_gas),
    ELECTRIC(R.drawable.ic_cate_electric),
    PHONE_BILL(R.drawable.ic_cate_phone_bill),
    WATER_BILL(R.drawable.ic_cate_water_bill),
    HEALTHCARE(R.drawable.ic_cate_healthcare),
    TRAVEL(R.drawable.ic_cate_travel),
    CLOTHES(R.drawable.ic_cate_clothes),
    SHOES(R.drawable.ic_cate_shoes),
    SHOPPING(R.drawable.ic_cate_shopping),
    EDUCATION(R.drawable.ic_cate_education),
    ENTERTAINMENT(R.drawable.ic_cate_entertainment),
    INTERNET(R.drawable.ic_cate_internet),
    INCOME_SALARY(R.drawable.ic_cate_salary),
    INCOME_GIFT(R.drawable.ic_cate_gift);

    private final int resourceId;
    TransactionCategory(int resourceId){
        this.resourceId = resourceId;
    }
    public int getResourceId(){
        return resourceId;
    }
}

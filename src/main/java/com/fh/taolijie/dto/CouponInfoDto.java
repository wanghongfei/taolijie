package com.fh.taolijie.dto;

import com.fh.taolijie.domain.CouponModel;

/**
 * Created by whf on 11/19/15.
 */
public class CouponInfoDto {
    private CouponModel coupon;

    private Integer total;
    private Integer left;

    public CouponInfoDto(CouponModel coupon, Integer tot, Integer left) {
        this.coupon = coupon;

        this.total = tot;
        this.left = left;
    }

    public CouponModel getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponModel coupon) {
        this.coupon = coupon;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }
}

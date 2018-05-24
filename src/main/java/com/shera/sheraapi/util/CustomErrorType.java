/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shera.sheraapi.util;

/**
 *
 * @author jirasak_ka
 */
public class CustomErrorType {

    private String errorMessageEN = "";
    private String errorMEssageTH = "";

    public CustomErrorType(String errorMessageTH, String errorMEssageEN) {
        this.errorMEssageTH = errorMessageTH;
        this.errorMessageEN = errorMEssageEN;
    }

    public String getErrorMessageEN() {
        return errorMessageEN;
    }

    public void setErrorMessageEN(String errorMessageEN) {
        this.errorMessageEN = errorMessageEN;
    }

    public String getErrorMEssageTH() {
        return errorMEssageTH;
    }

    public void setErrorMEssageTH(String errorMEssageTH) {
        this.errorMEssageTH = errorMEssageTH;
    }

}

package com.yxun8.core.pojo.entity;

import com.yxun8.core.pojo.specification.Specification;
import com.yxun8.core.pojo.specification.SpecificationOption;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class SpecEntity implements Serializable {
    private Specification spec;
    private List<SpecificationOption> specOption;

    public Specification getSpec() {
        return spec;
    }

    public void setSpec(Specification spec) {
        this.spec = spec;
    }


    public List<SpecificationOption> getSpecOption() {
        return specOption;
    }

    public void setSpecOption(List<SpecificationOption> specOption) {
        this.specOption = specOption;
    }

    @Override
    public String toString() {
        return "SpecEntity{" +
                "spec=" + spec +
                ", specOption=" + specOption +
                '}';
    }
}

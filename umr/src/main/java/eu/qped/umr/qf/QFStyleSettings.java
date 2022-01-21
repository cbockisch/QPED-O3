package eu.qped.umr.qf;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class QFStyleSettings {

    private String mainLevel;

    private String basisLevel;
    private String compLevel;
    private String namesLevel;

    private String classLength;
    private String methodLength;
    private String cycloComplexity;
    private String fieldsCount;

    private String varName;
    private String methodName;
    private String className;


	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }



	public boolean hasProperty(String property) {
		return additionalProperties.containsKey(property);
	}



	@JsonAnySetter
	public void setAdditionalProperty(String property, String value){
		additionalProperties.put(property, value);
	}



    public String getBasisLevel() {
        return basisLevel;
    }

    public String getClassLength() {
        return classLength;
    }

    public String getMethodLength() {
        return methodLength;
    }

    public String getCycloComplexity() {
        return cycloComplexity;
    }

    public String getFieldsCount() {
        return fieldsCount;
    }

    public String getVarName() {
        return varName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getCompLevel() {
        return compLevel;
    }

    public void setCompLevel(String compLevel) {
        this.compLevel = compLevel;
    }

    public String getNamesLevel() {
        return namesLevel;
    }

    public void setNamesLevel(String namesLevel) {
        this.namesLevel = namesLevel;
    }


    public void setBasisLevel(String basisLevel) {
        this.basisLevel = basisLevel;
    }

    public void setClassLength(String classLength) {
        this.classLength = classLength;
    }

    public void setMethodLength(String methodLength) {
        this.methodLength = methodLength;
    }

    public void setCycloComplexity(String cycloComplexity) {
        this.cycloComplexity = cycloComplexity;
    }

    public void setFieldsCount(String fieldsCount) {
        this.fieldsCount = fieldsCount;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getMainLevel() {
        return mainLevel;
    }

    public void setMainLevel(String mainLevel) {
        this.mainLevel = mainLevel;
    }
}

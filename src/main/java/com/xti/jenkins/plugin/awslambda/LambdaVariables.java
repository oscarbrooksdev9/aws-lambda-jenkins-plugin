package com.xti.jenkins.plugin.awslambda;

/*
 * #%L
 * AWS Lambda Upload Plugin
 * %%
 * Copyright (C) 2015 XT-i
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import hudson.EnvVars;
import hudson.Extension;
import hudson.Util;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.ListBoxModel;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

public class LambdaVariables extends AbstractDescribableImpl<LambdaVariables> {
    private String awsAccessKeyId;
    private String awsSecretKey;
    private String awsRegion;
    private String artifactLocation;
    private String description;
    private String functionName;
    private String handler;
    private Integer memorySize;
    private String role;
    private String runtime;
    private Integer timeout;
    private boolean successOnly;
    private String updateMode;

    @DataBoundConstructor
    public LambdaVariables(String awsAccessKeyId, String awsSecretKey, String awsRegion, String artifactLocation, String description, String functionName, String handler, Integer memorySize, String role, String runtime, Integer timeout, boolean successOnly, String updateMode) {
        this.awsAccessKeyId = awsAccessKeyId;
        this.awsSecretKey = awsSecretKey;
        this.awsRegion = awsRegion;
        this.artifactLocation = artifactLocation;
        this.description = description;
        this.functionName = functionName;
        this.handler = handler;
        this.memorySize = memorySize;
        this.role = role;
        this.runtime = runtime;
        this.timeout = timeout;
        this.successOnly = successOnly;
        this.updateMode = updateMode;
    }

    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public String getArtifactLocation() {
        return artifactLocation;
    }

    public String getDescription() {
        return description;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getHandler() {
        return handler;
    }

    public Integer getMemorySize() {
        return memorySize;
    }

    public String getRole() {
        return role;
    }

    public String getRuntime() {
        return runtime;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public String getUpdateMode() {
        return updateMode;
    }

    public boolean getSuccessOnly(){
        return successOnly;
    }

    public void setAwsAccessKeyId(String awsAccessKeyId) {
        this.awsAccessKeyId = awsAccessKeyId;
    }

    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public void setArtifactLocation(String artifactLocation) {
        this.artifactLocation = artifactLocation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public void setMemorySize(Integer memorySize) {
        this.memorySize = memorySize;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void setUpdateMode(String updateMode) {
        this.updateMode = updateMode;
    }

    public void setSuccessOnly(boolean successOnly) {
        this.successOnly = successOnly;
    }

    public void expandVariables(EnvVars env) {
        awsAccessKeyId = expand(awsAccessKeyId, env);
        awsSecretKey = expand(awsSecretKey, env);
        awsRegion = expand(awsRegion, env);
        artifactLocation = expand(artifactLocation, env);
        description = expand(description, env);
        functionName = expand(functionName, env);
        handler = expand(handler, env);
        role = expand(role, env);
        runtime = expand(runtime, env);
    }

    public LambdaVariables getClone(){
        return new LambdaVariables(awsAccessKeyId, awsSecretKey, awsRegion, artifactLocation, description, functionName, handler, memorySize, role, runtime, timeout, successOnly, updateMode);
    }

    private String expand(String value, EnvVars env) {
        return Util.replaceMacro(value.trim(), env);
    }

    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static class DescriptorImpl extends Descriptor<LambdaVariables> {

        /* TODO: conditionally check based on UpdateMode
        public FormValidation doCheckTimeout(@QueryParameter String value) {
            try {
                Integer.parseInt(value);
                return FormValidation.ok();
            } catch (NumberFormatException e) {
                return FormValidation.error("Not a number");
            }
        }
        */

        /* TODO: conditionally check based on UpdateMode
        public FormValidation doCheckMemorySize(@QueryParameter String value) {
            try {
                Integer.parseInt(value);
                return FormValidation.ok();
            } catch (NumberFormatException e) {
                return FormValidation.error("Not a number");
            }
        }
        */

        public ListBoxModel doFillUpdateModeItems(@QueryParameter String updateMode) {
            ListBoxModel items = new ListBoxModel();
            for (UpdateModeValue updateModeValue : UpdateModeValue.values()) {
                items.add(new ListBoxModel.Option(updateModeValue.getDisplayName(), updateModeValue.getMode(), updateModeValue.getMode().equals(updateMode)));
            }
            return items;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Deploy into Lambda";
        }


    }
}

package com.common.people;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;

public class CheckSnapShotBuilder extends Builder implements SimpleBuildStep {
    private final String pomXmlPath;
    private final String buildType;

    @DataBoundConstructor
    public CheckSnapShotBuilder(String pomXmlPath, String buildType) {
        this.pomXmlPath = pomXmlPath;
        this.buildType = buildType;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath filePath, Launcher launcher, TaskListener taskListener) throws InterruptedException, IOException {

    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckPomXmlPath(@QueryParameter String value){
            if(value.length() == 0){
                return FormValidation.error("the path can not be empty!");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckbuildType(@QueryParameter String value){
            if(value.length() == 0){
                return FormValidation.error("the type can be maven or gradle!");
            }
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "CheckSnapshot";
        }
    }
}

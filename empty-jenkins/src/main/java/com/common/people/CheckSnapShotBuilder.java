package com.common.people;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.*;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;

public class CheckSnapShotBuilder extends Builder implements SimpleBuildStep {
    private final String pomXmlPath;
    private final String buildType;
    private final boolean createDependencyTree;


    @DataBoundConstructor
    public CheckSnapShotBuilder(String pomXmlPath, String buildType, boolean createDependencyTree) {
        this.pomXmlPath = pomXmlPath;
        this.buildType = buildType;
        this.createDependencyTree = createDependencyTree;
    }

    public String getPomXmlPath() {
        return pomXmlPath;
    }

    public String getBuildType() {
        return buildType;
    }

    public boolean isCreateDependencyTree() {
        return createDependencyTree;
    }

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckPomXmlPath(@QueryParameter String value){
            if(value.length() == 0){
                return FormValidation.error("the path can not be empty!");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckBuildType(@QueryParameter String value){
            if(value.length() == 0){
                return FormValidation.error("the type can be maven or gradle!");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckCreateDependencyTree(@QueryParameter String value){
            if(value.equals(null)){
                return FormValidation.error("if you do not choose, default choose not create!");
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

    @Override
    public void perform(@NonNull Run<?, ?> run, @NonNull FilePath workspace, @NonNull EnvVars env, @NonNull Launcher launcher, @NonNull TaskListener listener) throws InterruptedException, IOException {
        ScanSnapshot.checkPom(workspace.toString() + "/" +pomXmlPath, listener);
        listener.getLogger().println(workspace.toString() + "/" +pomXmlPath);
        listener.error("aaaaaaa");

    }
}

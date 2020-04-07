package com.github.chMatvey.action

import com.intellij.execution.RunManagerEx
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.externalSystem.action.ExternalSystemAction
import com.intellij.openapi.externalSystem.model.execution.ExternalSystemTaskExecutionSettings
import com.intellij.openapi.externalSystem.model.execution.ExternalTaskExecutionInfo
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.project.Project
import org.jetbrains.plugins.gradle.util.GradleConstants


class ShowFileContentAction : ExternalSystemAction() {
    private val gradleShowTask = "show"
    private val taskArgument = "--path="

    override fun actionPerformed(e: AnActionEvent) {
        val fileChooserDescriptor = FileChooserDescriptor(
            true,
            false,
            false,
            false,
            false,
            false
        )
        fileChooserDescriptor.title = "Show file content"
        fileChooserDescriptor.description = "Choose a file to display its contents"

        FileChooser.chooseFile(fileChooserDescriptor, e.project, null) {
            val project = e.getRequiredData(CommonDataKeys.PROJECT)
            val tasks = listOf(gradleShowTask, taskArgument + it.path)
            val settings = ExternalSystemTaskExecutionSettings()

            settings.externalProjectPath = project.basePath
            settings.taskNames = tasks
            settings.externalSystemIdString = GradleConstants.SYSTEM_ID.toString()

            runGradle(
                project,
                ExternalTaskExecutionInfo(
                    settings,
                    DefaultRunExecutor.EXECUTOR_ID
                )
            )
        }
    }

    private fun runGradle(project: Project, taskExecutionInfo: ExternalTaskExecutionInfo) {
        ExternalSystemUtil.runTask(
            taskExecutionInfo.settings,
            taskExecutionInfo.executorId,
            project,
            GradleConstants.SYSTEM_ID
        )

        val configuration =
            ExternalSystemUtil.createExternalSystemRunnerAndConfigurationSettings(
                taskExecutionInfo.settings,
                project,
                GradleConstants.SYSTEM_ID
            ) ?: return

        val runManager = RunManagerEx.getInstanceEx(project)
        val existingConfiguration = runManager.findConfigurationByName(configuration.name)

        if (existingConfiguration == null) {
            runManager.setTemporaryConfiguration(configuration)
        } else {
            runManager.selectedConfiguration = existingConfiguration
        }
    }

    override fun update(e: AnActionEvent) {
        val presentation = e.presentation
        presentation.isVisible = isVisible(e)
        presentation.isEnabled = true
    }
}

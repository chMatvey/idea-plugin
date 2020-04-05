package com.github.chMatvey.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.ui.Messages

class OpenShowFileContentDialogAction : AnAction() {
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
            Messages.showMessageDialog(e.project, it.path, "Content", Messages.getInformationIcon())
        }
    }
}

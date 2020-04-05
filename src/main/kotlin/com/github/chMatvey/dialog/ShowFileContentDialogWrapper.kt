package com.github.chMatvey.dialog

import com.intellij.openapi.ui.DialogWrapper
import java.awt.BorderLayout
import java.io.File
import javax.swing.JComponent
import javax.swing.JPanel

class ShowFileContentDialogWrapper(val file: File) : DialogWrapper(true) {
    init {
        init()
        title = "Show file content"
    }

    override fun createCenterPanel(): JComponent? {
        return JPanel(BorderLayout())
    }
}

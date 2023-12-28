package com.github.jahseh618.demojetbrain.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.project.Project;

public class PopAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        VirtualFile virtualFile = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);

        Document document = editor.getDocument();
        String extension = virtualFile.getExtension();
        if (!"md".equalsIgnoreCase(extension)) {
            String title = String.format("文件类型错误");
            String message = "<br>该功能仅对markdown文件有效，请在正确的文件下执行此操作！<br>";
            Messages.showMessageDialog(project, message, title, Messages.getInformationIcon());
            return;
        }

        String[] lines = document.getText().split("\n");


    }
}

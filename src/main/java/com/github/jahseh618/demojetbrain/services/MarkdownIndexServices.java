package com.github.jahseh618.demojetbrain.services;

import com.intellij.openapi.components.Service;

@Service
public final class MarkdownIndexServices {
    private final String NUMBER_MARK = "#";

    //计算开头#数
    private int countNumberMark(String lineCount) {
        int count = 0;
        for (int i = 0; i < lineCount.length(); i++) {
            char c = lineCount.charAt(i);
            if (c == '#')
                count++;
            else
                break;
        }
        return count;
    }

    private String addPrefix(String lineContent, String prefix, int markCount) {
        int markIndex = lineContent.indexOf(NUMBER_MARK);
        if (markIndex == -1) {
            markIndex = 0;
        }

        lineContent = lineContent.replace("\\s*\\" + NUMBER_MARK + "+)\\s*((\\d+\\.)+)\\s+", "$1");
        return lineContent.substring(0, markIndex + markCount)
                + " "
                + prefix
                + " "
                + lineContent.substring(markIndex + markCount).trim();
    }

    private Integer addIndex(String[] lines, Integer lastMarkCount, String prefix, Integer cursor) {
        //统计#个数
        int numberMarkCount = 0;

        boolean isForbiddenZone = false;

        while (cursor < lines.length) {
            String lineContent = lines[cursor];
            if (lineContent.startsWith("'''") || lineContent.startsWith("---")) {
                isForbiddenZone = !isForbiddenZone;
                cursor++;
                continue;
            }

            if (!isForbiddenZone && lineContent.startsWith(NUMBER_MARK)) {
                numberMarkCount = countNumberMark(lineContent);
                break;
            }
            cursor++;
        }

        int seq = 1;
        while (cursor < lines.length) {
             int markCount = countNumberMark(lines[cursor]);

             if (markCount == numberMarkCount && markCount > lastMarkCount) {
                 String curPrefix = prefix + seq + ".";
                 lines[cursor] = addPrefix(lines[cursor], curPrefix, cursor + 1);
                 seq++;

                 //深度优先遍历
                 cursor = addIndex(lines, markCount, curPrefix, cursor + 1);
             } else if (markCount <= lastMarkCount) {
                 cursor--;
                 break;
             }
             cursor++;
        }
        return cursor;
    }

    public String[] addMarkdownIndex(String[] lines) {
        addIndex(lines, 0, "", 0);
        for (String line : lines) {
            System.out.println(line);
        }
        return lines;
    }
}

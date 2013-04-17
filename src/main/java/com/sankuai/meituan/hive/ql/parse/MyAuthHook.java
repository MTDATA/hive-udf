package com.sankuai.meituan.hive.ql.parse;


import org.apache.hadoop.hive.ql.exec.Task;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.AbstractSemanticAnalyzerHook;
import org.apache.hadoop.hive.ql.parse.HiveParser;
import org.apache.hadoop.hive.ql.parse.HiveSemanticAnalyzerHookContext;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.session.SessionState;

import java.io.Serializable;
import java.util.List;

/**
 * 只运行Admin用户(sankuai用户)执行创建数据库，赋权等操作。
 */
public class MyAuthHook extends AbstractSemanticAnalyzerHook {

    private static String admin = "sankuai";

    @Override
    public ASTNode preAnalyze(HiveSemanticAnalyzerHookContext context, ASTNode ast) throws SemanticException {
        switch (ast.getToken().getType()) {
            case HiveParser.TOK_CREATEDATABASE:
            case HiveParser.TOK_DROPDATABASE:
            case HiveParser.TOK_CREATEROLE:
            case HiveParser.TOK_DROPROLE:
            case HiveParser.TOK_GRANT:
            case HiveParser.TOK_REVOKE:
            case HiveParser.TOK_GRANT_ROLE:
            case HiveParser.TOK_REVOKE_ROLE:
                String userName = null;
                if (SessionState.get() != null && SessionState.get().getAuthenticator() != null) {
                    userName = SessionState.get().getAuthenticator().getUserName();
                }
                if (!admin.equalsIgnoreCase(userName)) {
                    throw new SemanticException(userName + " can't use ADMIN options, except " + admin + ".");
                }
                break;
            default:
                break;
        }

        return ast;
    }

    @Override
    public void postAnalyze(HiveSemanticAnalyzerHookContext context, List<Task<? extends Serializable>> rootTasks) throws SemanticException {
        super.postAnalyze(context, rootTasks);
    }
}

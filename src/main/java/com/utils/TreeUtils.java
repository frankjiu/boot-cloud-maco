package com.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;


public class TreeUtils {

    /**
     * 构建 tree
     *
     * @param ts
     * @param <T>
     * @return
     */
    public static <T extends TreeNode> List<T> build(List<T> ts) {
        // 顶级节点的主键ID="7777ffff69b495f40169b7daba748888";
        List<T> treeRoot = ts.stream().filter(tree -> "7777ffff69b495f40169b7daba748888".equals(tree.getPid())).collect(Collectors.toList());
        treeRoot.forEach(root -> findTree(ts, root));
        return treeRoot;
    }

    /**
     * 递归获取无限级子节点
     *
     * @param ts
     * @param t
     * @return
     */
    private static <T extends TreeNode> T findTree(List<T> ts, T t) {
        List<T> childList = ts.stream().filter(t_ -> t.getId().equals(t_.getPid())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childList)) {
            childList.forEach(child -> t.addChild(findTree(ts, child)));
        }
        return t;
    }
}
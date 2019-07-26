package com.utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import lombok.Data;

@Data
public class TreeNode implements Serializable {

	private static final long serialVersionUID = 1L;

	//节点ID
    private String id;

	//节点父ID
    private String pid;
    
    //子节点集
    @Transient
    private List<TreeNode> children = new ArrayList<>();

    //添加子节点方法
    public void addChild(TreeNode node) {
        children.add(node);
    }

    // 初始化节点对象
    public TreeNode() {}
    public TreeNode(String id, String pid) {
        this.id = id;
        this.pid = pid;
    }
}
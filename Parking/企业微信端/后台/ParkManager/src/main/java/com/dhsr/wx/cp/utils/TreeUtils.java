package com.dhsr.wx.cp.utils;

import com.dhsr.wx.cp.entity.Combotree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: LockService
 * @Package: com.dhsr.sirelock.util
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: liuxiaogang
 * @CreateDate: 2019-05-14 17:23
 * @UpdateUser: liuxiaogang
 * @UpdateDate: 2019-05-14 17:23
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
public class TreeUtils {

    /**
     * @param listData // 从数据库中查询的数据
     * @return
     */
    public static List<Combotree> getTreeList(List<?> listData, String id, String parentId, String categoryName, String Code) throws Exception {

        List<Combotree> resultList = new ArrayList<Combotree>();  // 最终返回的结果
        Map<Integer, Object> map = new HashMap<Integer, Object>();

        for (int i = 0; i < listData.size() && !listData.isEmpty(); i++) {

            // 写一个与该方法差不多的方法，将得到TreeUtils的代码抽取出来
            // 也可以将listData集合整个转换成装有TreeUtils的集合x，然后再循环x
            Combotree combotrees = new Combotree();
            combotrees.setId(Integer.parseInt(Combotree.getFileValue(listData.get(i), id).toString())); // id              // 返回值为Object无法直接转换成Integer,先toString，再转换成Integer。这里的返回值写成Object是因为多种类型字段的值都可以用该方法
            combotrees.setParentId(Integer.parseInt(Combotree.getFileValue(listData.get(i), parentId).toString())); // 父id
            combotrees.setText(Combotree.getFileValue(listData.get(i), categoryName).toString());  // 节点名
            combotrees.setCode(Combotree.getFileValue(listData.get(i), Code).toString());  // 节点编号
            //System.out.println("节点名为+"+TreeUtils.getFileValue(listData.get(i),categoryName).toString());
            combotrees.setData(listData.get(i));   // data:原对象中的所有属性，无children

            // 通过反射得到每条数据的id将数据封装的map集合中，id为键，元素本身为值
            map.put(combotrees.getId(), combotrees);


            // 将所有顶层元素添加到resultList集合中
            //if( 0 == treeUtils.getParentId()){
            //   resultList.add(treeUtils);
            // }
        }
// 得到所有的顶层节点，游离节点也算作顶层节点
// 优点一，不用知道等级节点的id
// 优点而，只查询了一次数据库
        for (int i = 0; i < listData.size(); i++) {
            if (!map.containsKey(Integer.parseInt(Combotree.getFileValue(listData.get(i), parentId).toString()))) {
                resultList.add((Combotree) map.get(Integer.parseInt(Combotree.getFileValue(listData.get(i), id).toString())));
            }
        }


        for (int i = 0; i < listData.size() && !listData.isEmpty(); i++) {
            Combotree obj = (Combotree) map.get(Integer.parseInt(Combotree.getFileValue(listData.get(i), parentId).toString()));
            if (obj != null) {
                if (obj.getChildList() == null) {
                    obj.setChildList(new ArrayList());
                }
                obj.getChildList().add(map.get(Integer.parseInt(Combotree.getFileValue(listData.get(i), id).toString())));
            }
        }
        return resultList;
    }

}

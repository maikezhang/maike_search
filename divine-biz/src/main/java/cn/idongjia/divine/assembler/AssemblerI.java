package cn.idongjia.divine.assembler;

import cn.idongjia.common.base.Base;
import cn.idongjia.divine.dto.BaseDTO;
import cn.idongjia.se.lib.entities.BasicEntity;

import java.util.List;

/**
 * @author lc on 2018/7/28.
 * @class cn.idongjia.divine.assemble.AssemblerI
 */
public interface AssemblerI<E extends BasicEntity,D extends Base,T extends BaseDTO> {
    List<E> assemble(List<D> dos);

    E assemble(T t);
}

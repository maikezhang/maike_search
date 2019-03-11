package cn.idongjia.divine.utils.disruptor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public abstract class ValueWrapper<T> {

    private T value;

    private EventMeta meta;

    public ValueWrapper(EventMeta meta) {
        this.meta=meta;
    }
    public ValueWrapper() {
        this.meta=new EventMeta();
    }

}

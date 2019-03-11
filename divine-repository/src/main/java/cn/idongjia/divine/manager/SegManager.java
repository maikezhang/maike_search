package cn.idongjia.divine.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.idongjia.desert.dto.WordsDTO;
import cn.idongjia.desert.query.BatchSegQuery;
import cn.idongjia.desert.query.SegQuery;
import cn.idongjia.desert.service.SegService;
import cn.idongjia.divine.annotation.Elapsed;
import cn.idongjia.divine.utils.exception.DivineException;
import lombok.extern.slf4j.Slf4j;

/**
 * 分词服务管理
 *
 * @author lc
 * @create at 2018/8/3.
 */
@Component
@Slf4j
public class SegManager {

    @Resource
    private SegService segService;


    @Elapsed
    public List<WordsDTO> seg(SegQuery query) {
        query.setSegMode((short)1);
        query.setOutputStopWord(false);
        try {
            return segService.seg(query);
        } catch(Exception e) {
            log.error("调用cn.idongjia.desert.service.SegService.seg服务失败{}",e);
            throw DivineException.failure("分词失败");
        }
    }

    @Elapsed
    public List<List<WordsDTO>> batchSeg(BatchSegQuery query) {
        query.setSegMode((short)1);
        query.setOutputStopWord(false);
        try {
            return segService.batchSeg(query);
        } catch(Exception e) {
            log.error("调用cn.idongjia.desert.service.SegService.batchSeg服务失败{}",e);
            throw DivineException.failure("分词失败");
        }
    }
	
	@Elapsed
	public List<WordsDTO> segForIndex(SegQuery query) {
		query.setSegMode((short) 8);
		query.setOutputStopWord(false);
		try {
			return segService.seg(query);
		} catch (Exception e) {
			log.error("调用cn.idongjia.desert.service.SegService.batchSeg服务失败{}", e);
			throw DivineException.failure("分词失败");
		}
	}
	@Elapsed
	public List<List<WordsDTO>> batchSegForIndex(BatchSegQuery query) {
		query.setSegMode((short) 8);
		query.setOutputStopWord(false);
		try {
			return segService.batchSeg(query);
		} catch (Exception e) {
			log.error("调用cn.idongjia.desert.service.SegService.batchSeg服务失败{}", e);
			throw DivineException.failure("分词失败");
		}
	}

}

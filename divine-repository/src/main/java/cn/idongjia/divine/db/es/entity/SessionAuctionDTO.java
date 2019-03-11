package cn.idongjia.divine.db.es.entity;

import java.util.List;

import cn.idongjia.se.lib.dto.AggDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lc
 * @create at 2018/8/21.
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionAuctionDTO extends AggDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long			  sessionId;
    private List<AuctionDTO> auctionDTOS;
}

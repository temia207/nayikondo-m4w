package org.cwf.client.util;



import com.extjs.gxt.ui.client.Style.SortDir;
import org.m4water.server.admin.model.paging.PagingLoadConfig;
import org.m4water.server.admin.model.paging.PagingLoadResult;

public class PagingUtil {

	public static PagingLoadConfig createPagingLoadConfig(com.extjs.gxt.ui.client.data.PagingLoadConfig gxtPagingConfig) {

		Boolean sortDescending = false;
		if (gxtPagingConfig.getSortDir() == SortDir.DESC) {
			sortDescending = true;
		} else if (gxtPagingConfig.getSortDir() == SortDir.ASC) {
			sortDescending = false;
		}

		PagingLoadConfig ourPagingConfig = new PagingLoadConfig(gxtPagingConfig.getOffset(),
				gxtPagingConfig.getLimit(), gxtPagingConfig.getSortField(), sortDescending);

		return ourPagingConfig;
	}

	public static <Data> PagingLoadResult<Data> createPagingLoadResult(
			com.extjs.gxt.ui.client.data.PagingLoadResult<Data> gxtPagingResult) {
		PagingLoadResult<Data> ourPagingResult = new PagingLoadResult<Data>(gxtPagingResult.getData(),
				gxtPagingResult.getOffset(), gxtPagingResult.getTotalLength());
		return ourPagingResult;
	}
}

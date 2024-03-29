package com.xms.house.Controller;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xms.house.common.PageParams;
import com.xms.house.common.RestResponse;
import com.xms.house.model.Agency;
import com.xms.house.model.ListResponse;
import com.xms.house.model.User;
import com.xms.house.service.AgencyService;

@RestController
@RequestMapping("agency")
public class AgencyController {

  @Autowired
  private AgencyService agencyService;

  @RequestMapping("add")
  public RestResponse<Object> addAgency(@RequestBody Agency agency) {
    agencyService.add(agency);
    return RestResponse.success();
  }

  @RequestMapping("list")
  public RestResponse<List<Agency>> agencyList() {
    List<Agency> agencies = agencyService.getAllAgency();
    return RestResponse.success(agencies);
  }


  /**
   * 获取经纪人分页列表
   * @param limit
   * @param offset
   * @return
   */
  @RequestMapping("agentList")
  public RestResponse<ListResponse<User>> agentList(Integer limit, Integer offset) {
    PageParams pageParams = new PageParams();
    pageParams.setLimit(limit);
    pageParams.setOffset(offset);
    Pair<List<User>, Long> pair = agencyService.getAllAgent(pageParams);
    ListResponse<User> response = ListResponse.build(pair.getKey(), pair.getValue());
    return RestResponse.success(response);
  }

  /**
   * 获取经纪人详情页
   * @param id
   * @return
   */
  @RequestMapping("agentDetail")
  public RestResponse<User> agentDetail(Long id) {
    User user = agencyService.getAgentDetail(id);
    return RestResponse.success(user);
  }

  @RequestMapping("agencyDetail")
  public RestResponse<Agency> agencyDetail(Integer id) {
    Agency agency = agencyService.getAgency(id);
    return RestResponse.success(agency);
  }

}


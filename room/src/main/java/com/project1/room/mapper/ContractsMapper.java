package com.project1.room.mapper;

import com.project1.room.dto.request.ContractsRequest;
import com.project1.room.dto.response.ContractsResponse;
import com.project1.room.entity.Contracts;
import com.project1.room.util.TimestampUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.sql.Timestamp;

@Mapper(componentModel = "spring")
public interface ContractsMapper {
    @Named("stringToTimestamp")
    @Mapping(target = "endDate", expression = "java(stringToTimestamp(request.getEndDate()))")
    @Mapping(target = "startDate", expression = "java(stringToTimestamp(request.getStartDate()))")
    @Mapping(target = "room", ignore = true)
    Contracts toContract(ContractsRequest request);

    @Named("timestampToString")
    @Mapping(target = "startDate", expression = "java(timestampToString(contracts.getStartDate()))")
    @Mapping(target = "endDate", expression = "java(timestampToString(contracts.getEndDate()))")
    ContractsResponse toContractsResponse(Contracts contracts);

    @Mapping(target = "endDate", expression = "java(stringToTimestamp(request.getEndDate()))")
    @Mapping(target = "startDate", expression = "java(stringToTimestamp(request.getStartDate()))")
    @Mapping(target = "room", ignore = true)
    void updateContract(@MappingTarget Contracts contract, ContractsRequest request);

    default Timestamp stringToTimestamp(String dateString) {
        return TimestampUtil.stringToTimestamp(dateString);
    }

    default String timestampToString(Timestamp timestamp) {
        return TimestampUtil.timestampToString(timestamp);
    }
}

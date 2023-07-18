package com.flb.jobfetcher.infra.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
class JobAdResponseDto {

    private List<JobAdDto> resultats;

}

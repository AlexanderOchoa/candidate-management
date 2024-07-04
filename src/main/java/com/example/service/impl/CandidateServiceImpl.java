package com.example.service.impl;

import com.example.cadidate_management.CandidatesApiDelegate;
import com.example.cadidate_management.model.*;
import com.example.entity.Candidate;
import com.example.exception.Exception400;
import com.example.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidatesApiDelegate {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public ResponseEntity<CreateCandidateResponse> create(CreateCandidateRequest request) {
        Candidate candidate = Candidate.builder()
                .name(request.getName())
                .email(request.getEmail())
                .gender(request.getGender())
                .salaryExpected(request.getSalaryExpected().doubleValue())
                .status("created")
                .build();

        Candidate candidateSaved = candidateRepository.save(candidate);

        CreateCandidateDataResponse createCandidateDataResponse = new CreateCandidateDataResponse();
        createCandidateDataResponse.setId(candidateSaved.getId().intValue());

        Metadata metadata = new Metadata();
        metadata.setMessage("Create success");
        metadata.setStatus(200);

        CreateCandidateResponse response = new CreateCandidateResponse();
        response.setData(createCandidateDataResponse);
        response.setMetadata(metadata);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeleteCandidateResponse> delete(BigDecimal id) {
        Optional<Candidate> candidate = candidateRepository.findById(id.longValue());

        if (candidate.isEmpty()) {
            throw new Exception400();
        }

        candidate.get().setStatus("deleted");

        candidateRepository.save(candidate.get());

        Metadata metadata = new Metadata();
        metadata.setMessage("Delete success");
        metadata.setStatus(200);

        DeleteCandidateResponse response = new DeleteCandidateResponse();
        response.setMetadata(metadata);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ListCandidateResponse> list() {
        List<Candidate> candidates = candidateRepository.findAll();

        if (candidates.isEmpty()) {
            throw new Exception400();
        }

        List<ListCandidateDataResponse> listCandidateDataResponses =  candidates.stream().map(candidate -> {
            ListCandidateDataResponse listCandidateDataResponse = new ListCandidateDataResponse();
            listCandidateDataResponse.setId(candidate.getId().intValue());
            listCandidateDataResponse.setName(candidate.getName());
            listCandidateDataResponse.setEmail(candidate.getEmail());
            listCandidateDataResponse.setGender(candidate.getGender());
            listCandidateDataResponse.setSalaryExpected(new BigDecimal(candidate.getSalaryExpected()));
            listCandidateDataResponse.setStatus(candidate.getStatus());
            return listCandidateDataResponse;
        }).collect(Collectors.toList());

        Metadata metadata = new Metadata();
        metadata.setMessage("Get success");
        metadata.setStatus(200);

        ListCandidateResponse response = new ListCandidateResponse();
        response.setData(listCandidateDataResponses);
        response.setMetadata(metadata);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetCandidateResponse> get(Integer id) {
        Optional<Candidate> candidate = candidateRepository.findById(id.longValue());

        if (candidate.isEmpty()) {
            throw new Exception400();
        }

        Metadata metadata = new Metadata();
        metadata.setMessage("Get success");
        metadata.setStatus(200);

        GetCandidateDataResponse getCandidateDataResponse = new GetCandidateDataResponse();
        getCandidateDataResponse.setId(candidate.get().getId().intValue());
        getCandidateDataResponse.setName(candidate.get().getName());
        getCandidateDataResponse.setEmail(candidate.get().getEmail());
        getCandidateDataResponse.setGender(candidate.get().getGender());
        getCandidateDataResponse.setSalaryExpected(new BigDecimal(candidate.get().getSalaryExpected()));
        getCandidateDataResponse.setStatus(candidate.get().getStatus());

        GetCandidateResponse response = new GetCandidateResponse();
        response.setData(getCandidateDataResponse);
        response.setMetadata(metadata);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UpdateCandidateResponse> udpate(UpdateCandidateRequest request) {
        Optional<Candidate> candidate = candidateRepository.findById(request.getId().longValue());

        if (candidate.isEmpty()) {
            throw new Exception400();
        }

        candidate.get().setName(request.getName());
        candidate.get().setEmail(request.getEmail());
        candidate.get().setGender(request.getGender());
        candidate.get().setSalaryExpected(request.getSalaryExpected().doubleValue());
        candidate.get().setStatus("updated");

        candidateRepository.save( candidate.get());

        Metadata metadata = new Metadata();
        metadata.setMessage("Update success");
        metadata.setStatus(200);

        UpdateCandidateResponse response = new UpdateCandidateResponse();
        response.setMetadata(metadata);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

package org.latheild.user.dao;

import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.common.domain.DAOResponse;
import org.latheild.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.utils.DAORequestJSONWrapper.FIND_ONE;
import static org.latheild.common.constant.BaseURI.*;

@SuppressWarnings("unchecked")
public class UserRepositoryImpl extends DAOResponseJSONAnalyzer implements UserRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/user";

    private Wrapper<User> analyzer = new Wrapper<>(new User());

    @Override
    public User findById(String id) {
        return (User) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON("id", id, FIND_ONE),
                        DAOResponse.class
                )
        );
    }

    @Override
    public User findByEmail(String email) {
        return (User) analyzer.analyze(restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_FIND
                ),
                DAORequestJSONWrapper.setFindRequestJSON("email", email, FIND_ONE),
                DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<User> findAll() {
        return (ArrayList<User>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        "",
                        DAOResponse.class
                )
        );
    }

    @Override
    public int countById(String id) {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON("id", id),
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }

    @Override
    public int countByEmail(String email) {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON("email", email),
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }

    @Override
    public void deleteById(String id) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("id", id),
                DAOResponse.class
        );
    }

    @Override
    public void deleteByEmail(String email) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("email", email),
                DAOResponse.class
        );
    }

    @Override
    public void deleteAll() {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                "",
                DAOResponse.class
        );
    }

    @Override
    public void save(User user) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("email");
        fieldNames.add("password");
        if (user.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, user),
                    DAOResponse.class
            );
        } else {
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_CREATE
                    ),
                    DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, user),
                    DAOResponse.class
            );
        }
    }

    @Override
    public int count() {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                "",
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }
}

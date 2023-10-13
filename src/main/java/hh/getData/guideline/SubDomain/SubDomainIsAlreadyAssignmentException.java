package hh.getData.guideline.SubDomain;

import java.text.MessageFormat;

public class SubDomainIsAlreadyAssignmentException extends RuntimeException {
    public SubDomainIsAlreadyAssignmentException(final Long subDomainId, final Long workDomainId ){
        super(MessageFormat.format("SubDomain:{0}is already assign to this WorkDomain {1}",subDomainId ,workDomainId));

    }
}

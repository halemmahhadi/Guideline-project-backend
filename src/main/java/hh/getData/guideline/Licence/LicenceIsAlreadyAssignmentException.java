package hh.getData.guideline.Licence;

import java.text.MessageFormat;

public class LicenceIsAlreadyAssignmentException extends RuntimeException {
    public LicenceIsAlreadyAssignmentException(final Long licenceId,final Long officeId ){
        super(MessageFormat.format("licence:{0}is already assign to office {1}",licenceId ,officeId));

    }
}

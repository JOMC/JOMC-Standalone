// SECTION-START[License Header]
// <editor-fold defaultstate="collapsed" desc=" Generated License ">
/*
 *   Copyright (c) 2010 The JOMC Project
 *   Copyright (c) 2005 Christian Schulte <cs@jomc.org>
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions
 *   are met:
 *
 *     o Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     o Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided with the
 *       distribution.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE JOMC PROJECT AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *   THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *   PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE JOMC PROJECT OR
 *   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 *   OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *   WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *   OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *   ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *   $Id$
 *
 */
// </editor-fold>
// SECTION-END
package org.jomc.standalone.model.support;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.xml.bind.JAXBElement;
import org.jomc.model.Implementation;
import org.jomc.model.ModelContext;
import org.jomc.model.ModelException;
import org.jomc.model.ModelValidationReport;
import org.jomc.model.ModelValidator;
import org.jomc.model.Module;
import org.jomc.model.Modules;
import org.jomc.model.ObjectFactory;
import org.jomc.standalone.model.MethodType;
import org.jomc.standalone.model.MethodsType;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Standalone 'ModelValidator' implementation.
 *
 * @author <a href="mailto:cs@jomc.org">Christian Schulte</a> 1.0
 * @version $Id$
 */
// </editor-fold>
// SECTION-END
// SECTION-START[Annotations]
// <editor-fold defaultstate="collapsed" desc=" Generated Annotations ">
@javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                             comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-17-SNAPSHOT/jomc-tools" )
// </editor-fold>
// SECTION-END
public class StandaloneModelValidator implements ModelValidator
{
    // SECTION-START[StandaloneModelValidator]

    public ModelValidationReport validateModel( final ModelContext modelContext, final Modules modules )
        throws ModelException
    {
        final ModelValidationReport report = new ModelValidationReport();

        for ( Module m : modules.getModule() )
        {
            if ( m.getImplementations() != null )
            {
                for ( Implementation i : m.getImplementations().getImplementation() )
                {
                    for ( Object any : i.getAny() )
                    {
                        final JAXBElement detailElement = new ObjectFactory().createImplementation( i );

                        if ( any instanceof JAXBElement )
                        {
                            any = ( (JAXBElement) any ).getValue();
                        }

                        if ( any instanceof MethodsType )
                        {
                            final MethodsType methodsType = (MethodsType) any;

                            if ( methodsType.getExceptions() != null &&
                                 methodsType.getExceptions().getDefaultException() != null &&
                                 methodsType.getExceptions().getDefaultException().getTargetClass() != null )
                            {
                                report.getDetails().add( new ModelValidationReport.Detail(
                                    "IMPLEMENTATION_METHODS_DEFAULT_EXCEPTION_TARGET_CLASS_CONSTRAINT", Level.SEVERE,
                                    getMessage( "implementationMethodsDefaultExceptionTargetClassConstraint",
                                                i.getIdentifier(),
                                                methodsType.getExceptions().getDefaultException().getClazz(),
                                                methodsType.getExceptions().getDefaultException().getTargetClass() ),
                                    detailElement ) );

                            }

                            for ( MethodType methodType : methodsType.getMethod() )
                            {
                                if ( methodType.getExceptions() != null &&
                                     methodType.getExceptions().getDefaultException() != null &&
                                     methodType.getExceptions().getDefaultException().getTargetClass() != null )
                                {
                                    report.getDetails().add( new ModelValidationReport.Detail(
                                        "IMPLEMENTATION_METHOD_DEFAULT_EXCEPTION_TARGET_CLASS_CONSTRAINT", Level.SEVERE,
                                        getMessage( "implementationMethodDefaultExceptionTargetClassConstraint",
                                                    i.getIdentifier(), methodType.getName(),
                                                    methodsType.getExceptions().getDefaultException().getClazz(),
                                                    methodsType.getExceptions().getDefaultException().getTargetClass() ),
                                        detailElement ) );

                                }
                            }
                        }
                    }
                }
            }
        }

        return report;
    }

    private static String getMessage( final String key, final Object... arguments )
    {
        return MessageFormat.format( ResourceBundle.getBundle(
            StandaloneModelValidator.class.getName().replace( '.', '/' ) ).getString( key ), arguments );

    }

    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">

    /** Creates a new {@code StandaloneModelValidator} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.JavaSources",
                                 comments = "See http://jomc.sourceforge.net/jomc/1.0-alpha-17-SNAPSHOT/jomc-tools" )
    public StandaloneModelValidator()
    {
        // SECTION-START[Default Constructor]
        super();
        // SECTION-END
    }
    // </editor-fold>
    // SECTION-END
    // SECTION-START[Dependencies]
    // SECTION-END
    // SECTION-START[Properties]
    // SECTION-END
    // SECTION-START[Messages]
    // SECTION-END
}
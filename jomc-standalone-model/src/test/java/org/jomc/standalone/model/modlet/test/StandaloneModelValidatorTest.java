// SECTION-START[License Header]
// <editor-fold defaultstate="collapsed" desc=" Generated License ">
/*
 *   Copyright (C) 2009 - 2011 The JOMC Project
 *   Copyright (C) 2005 - 2011 Christian Schulte <schulte2005@users.sourceforge.net>
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
package org.jomc.standalone.model.modlet.test;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import org.jomc.model.ModelObject;
import org.jomc.model.Module;
import org.jomc.model.Modules;
import org.jomc.model.modlet.ModelHelper;
import org.jomc.modlet.Model;
import org.jomc.modlet.ModelContext;
import org.jomc.modlet.ModelValidationReport;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

// SECTION-START[Documentation]
// <editor-fold defaultstate="collapsed" desc=" Generated Documentation ">
/**
 * Test cases for class {@code org.jomc.standalone.model.modlet.StandaloneModelValidator}.
 *
 * <p>
 *   This implementation is identified by identifier {@code <org.jomc.standalone.model.modlet.test.StandaloneModelValidatorTest>}.
 *   It does not provide any specified objects.
 * </p>
 *
 * @author <a href="mailto:schulte2005@users.sourceforge.net">Christian Schulte</a> 1.0
 * @version 1.0-beta-3-SNAPSHOT
 */
// </editor-fold>
// SECTION-END
// SECTION-START[Annotations]
// <editor-fold defaultstate="collapsed" desc=" Generated Annotations ">
@javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
// </editor-fold>
// SECTION-END
public class StandaloneModelValidatorTest
{
    // SECTION-START[StandaloneModelValidatorTest]

    @Test public void testValidateModel() throws Exception
    {
        final ModelContext context = ModelContext.createModelContext( this.getClass().getClassLoader() );
        final Unmarshaller u = context.createUnmarshaller( ModelObject.MODEL_PUBLIC_ID );

        final Modules modules = new Modules();
        final JAXBElement<Module> module =
            (JAXBElement<Module>) u.unmarshal( this.getClass().getResourceAsStream( "testsuite.xml" ) );

        modules.getModule().add( module.getValue() );

        final Model model = new Model();
        model.setIdentifier( ModelObject.MODEL_PUBLIC_ID );
        ModelHelper.setModules( model, modules );

        final ModelValidationReport report = context.validateModel( model );
        assertFalse( report.isModelValid() );

        for ( ModelValidationReport.Detail d : report.getDetails() )
        {
            System.out.println( d );
        }

        assertEquals( 1, report.getDetails( "IMPLEMENTATION_METHODS_DEFAULT_EXCEPTION_TARGET_CLASS_CONSTRAINT" ).size() );
        assertEquals( 1, report.getDetails( "IMPLEMENTATION_METHOD_DEFAULT_EXCEPTION_TARGET_CLASS_CONSTRAINT" ).size() );
    }

    // SECTION-END
    // SECTION-START[Constructors]
    // <editor-fold defaultstate="collapsed" desc=" Generated Constructors ">

    /** Creates a new {@code StandaloneModelValidatorTest} instance. */
    @javax.annotation.Generated( value = "org.jomc.tools.SourceFileProcessor 1.2-SNAPSHOT", comments = "See http://jomc.sourceforge.net/jomc/1.2/jomc-tools-1.2-SNAPSHOT" )
    public StandaloneModelValidatorTest()
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

/*
 *   Copyright (C) 2009 The JOMC Project. All rights reserved.
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
 *   THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 *   INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 *   AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 *   THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *   INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *   NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *   DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *   THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *   THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *   $JOMC$
 *
 */
package org.jomc.standalone.ri.naming.support;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;
import org.enhydra.jdbc.standard.StandardXADataSource;
import org.jomc.standalone.ri.StandaloneEnvironment;

/**
 * Provides static methods supporting XAPool.
 *
 * @author <a href="mailto:schulte2005@users.sourceforge.net">Christian Schulte</a> 1.0
 * @version $JOMC$
 */
abstract class XAPoolHelper
{

    XAPoolHelper()
    {
        super();
    }

    static void initializeXAPool( final StandaloneEnvironment environment, final Context context )
        throws NamingException
    {
        final TransactionManager transactionManager =
            (TransactionManager) context.lookup( environment.getTransactionManagerJndiName() );

        if ( context.lookup( environment.getDataSourceJndiName() ) instanceof StandardXADataSource )
        {
            final StandardXADataSource ds =
                (StandardXADataSource) context.lookup( environment.getDataSourceJndiName() );

            ds.setTransactionManager( transactionManager );
        }
        if ( context.lookup( environment.getJtaDataSourceJndiName() ) instanceof StandardXADataSource )
        {
            final StandardXADataSource ds =
                (StandardXADataSource) context.lookup( environment.getJtaDataSourceJndiName() );

            ds.setTransactionManager( transactionManager );
        }
    }

}

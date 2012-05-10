/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.jersey.media.multipart;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Custom {@link java.util.List} implementation that maintains parentage information automatically.
 *
 * @author Michal Gajdos (michal.gajdos at oracle.com)
 */
class BodyPartsList extends ArrayList<BodyPart> {

    MultiPart parent = null;

    BodyPartsList(MultiPart parent) {
        this.parent = parent;
    }

    @Override
    public boolean add(BodyPart bp) {
        super.add(bp);
        bp.setParent(parent);
        return true;
    }

    @Override
    public void add(int index, BodyPart bp) {
        super.add(index, bp);
        bp.setParent(parent);
    }

    @Override
    public boolean addAll(Collection<? extends BodyPart> bps) {
        if (super.addAll(bps)) {
            for (BodyPart bp : bps) {
                bp.setParent(parent);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends BodyPart> bps) {
        if (super.addAll(index, bps)) {
            for (BodyPart bp : bps) {
                bp.setParent(parent);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear() {
        BodyPart bps[] = super.toArray(new BodyPart[super.size()]);
        super.clear();
        for (BodyPart bp : bps) {
            bp.setParent(null);
        }
    }

    @Override
    public BodyPart remove(int index) {
        BodyPart bp = super.remove(index);
        if (bp != null) {
            bp.setParent(null);
        }
        return bp;
    }

    @Override
    public boolean remove(Object bp) {
        if (super.remove(bp)) {
            ((BodyPart) bp).setParent(null);
            return true;
        } else {
            return false;
        }
    }

}

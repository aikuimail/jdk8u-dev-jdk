/*
 * Copyright 2003 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

/*
 * @test
 * @bug 4955222
 * @summary Test that the releaseMBeanServer(MBeanServer mbeanServer) method
 *          throws IllegalArgumentException as expected
 * @author Luis-Miguel Alventosa
 * @run clean ReleaseMBeanServerTest
 * @run build ReleaseMBeanServerTest
 * @run main ReleaseMBeanServerTest
 */

/* Check that the releaseMBeanServer(MBeanServer mbeanServer) method throws
   IllegalArgumentException if mbeanServer was not generated by one of the
   createMBeanServer methods, or if releaseMBeanServer was already called
   on it. */

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

public class ReleaseMBeanServerTest {

    private static final String DOMAIN = "TestDomain";

    public static void main(String[] args) throws Exception {

        System.out.println("--------------------------------------" +
                           "-----------------------------------------");
        System.out.println("- Testing IllegalArgumentException in " +
                           "MBeanServerFactory.releaseMBeanServer() -");
        System.out.println("--------------------------------------" +
                           "-----------------------------------------");

        System.out.println("TEST_0: Call releaseMBeanServer() with " +
                           "a null MBeanServer reference.");
        try {
            MBeanServerFactory.releaseMBeanServer(null);
            System.err.println("Didn't get expected IllegalArgumentException!");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Got expected IllegalArgumentException!");
        }

        System.out.println("TEST_1: Call releaseMBeanServer() with an " +
                           "MBeanServer reference corresponding to an " +
                           "MBeanServer created using newMBeanServer().");
        MBeanServer mbs1 = MBeanServerFactory.newMBeanServer();
        try {
            MBeanServerFactory.releaseMBeanServer(mbs1);
            System.err.println("Didn't get expected IllegalArgumentException!");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Got expected IllegalArgumentException!");
        }

        System.out.println("TEST_2: Call releaseMBeanServer() with an " +
                           "MBeanServer reference corresponding to an " +
                           "MBeanServer created using newMBeanServer(String).");
        MBeanServer mbs2 = MBeanServerFactory.newMBeanServer(DOMAIN);
        try {
            MBeanServerFactory.releaseMBeanServer(mbs2);
            System.err.println("Didn't get expected IllegalArgumentException!");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Got expected IllegalArgumentException!");
        }

        System.out.println("TEST_3: Call releaseMBeanServer() twice with an " +
                           "MBeanServer reference corresponding to an MBean" +
                           "Server created using createMBeanServer().");
        MBeanServer mbs3 = MBeanServerFactory.createMBeanServer();
        MBeanServerFactory.releaseMBeanServer(mbs3);
        try {
            MBeanServerFactory.releaseMBeanServer(mbs3);
            System.err.println("Didn't get expected IllegalArgumentException!");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Got expected IllegalArgumentException!");
        }

        System.out.println("TEST_4: Call releaseMBeanServer() twice with an " +
                           "MBeanServer reference corresponding to an MBean" +
                           "Server created using createMBeanServer(String).");
        MBeanServer mbs4 = MBeanServerFactory.createMBeanServer(DOMAIN);
        MBeanServerFactory.releaseMBeanServer(mbs4);
        try {
            MBeanServerFactory.releaseMBeanServer(mbs4);
            System.err.println("Didn't get expected IllegalArgumentException!");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Got expected IllegalArgumentException!");
        }
    }
}

/*
 * This file is part of artnet4j.
 * 
 * Copyright 2009 Karsten Schmidt (PostSpectacular Ltd.)
 * 
 * artnet4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * artnet4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with artnet4j. If not, see <http://www.gnu.org/licenses/>.
 */

package org.nanuc.androidlight.app;


import java.net.SocketException;
import java.util.List;

import artnet4j.ArtNet;
import artnet4j.ArtNetException;
import artnet4j.ArtNetNode;
import artnet4j.events.ArtNetDiscoveryListener;
import artnet4j.packets.ArtDmxPacket;

public class AndroidLight implements ArtNetDiscoveryListener {

    public static void test1 (String[] args) {
        new AndroidLight().test();
    }

    private ArtNetNode netLynx;

    private int sequenceID;

    @Override
    public void discoveredNewNode(ArtNetNode node) {
        if (netLynx == null) {
            netLynx = node;
            System.out.println("found net lynx");
        }
    }

    @Override
    public void discoveredNodeDisconnected(ArtNetNode node) {
        System.out.println("node disconnected: " + node);
        if (node == netLynx) {
            netLynx = null;
        }
    }

    @Override
    public void discoveryCompleted(List<ArtNetNode> nodes) {
        System.out.println(nodes.size() + " nodes found:");
        for (ArtNetNode n : nodes) {
            System.out.println(n);
        }
    }
    public int run=1;
    public int wertR=0;
    public int wertG=0;
    @Override
    public void discoveryFailed(Throwable t) {
        System.out.println("discovery failed");
    }
   	public void test() {
        while (true) {
            ArtNet artnet = new ArtNet();
            ArtDmxPacket dmx2 = new ArtDmxPacket();
            try {
                artnet.start();
                artnet.getNodeDiscovery().addListener(this);
                //artnet.startNodeDiscovery();
                //System.out.println(dmx2.getData());
                int far = 0;
                int universe = 01;


                while (true) {
                    //System.out.println(run);
                    ArtDmxPacket dmx = new ArtDmxPacket();
                    dmx.setUniverse(0, 1);
                    byte[] buffer = new byte[510];


                    dmx.setSequenceID(sequenceID % 255);
                    if (run==1){
                        for (int i = 0; i < buffer.length - 3; i++) {
                            if (i % 3 == 0) {
                                buffer[i + far] =
                                        (byte) (20);
                            }
                        }
                    }
                    if (run==2){
                        for (int i = 0; i < buffer.length - 3; i++) {
                            if (i % 3 == 0) {
                                buffer[i] =
                                        (byte) (wertR);
                                buffer[i+1] =
                                        (byte) (wertG);
                            }
                        }
                    }
                   
                    far++;
                    if (far == 3) far = 0;
                    dmx.setDMX(buffer, buffer.length);
                    artnet.unicastPacket(dmx, "192.168.0.100");
                    sequenceID++;
                    //System.out.println(dmx.getSubnetID());
                    if (run==1)Thread.sleep(100);
                    else Thread.sleep(10);
                    while (run==0){
                        Thread.sleep(100);
                    }
                }
            } catch (SocketException e) {
                throw new AssertionError(e);
            } catch (ArtNetException e) {
                throw new AssertionError(e);
            } catch (InterruptedException e) {
                throw new AssertionError(e);
            }
        }
    }
}
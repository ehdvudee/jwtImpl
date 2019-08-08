package net.glaso.jwt.business.key.vo;

import java.util.Arrays;

public class KeyPairVo {
    private int seqId;
    private byte[] priKey;
    private byte[] cert;
    private boolean usage;
    private String kid;

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public byte[] getPriKey() {
        return priKey;
    }

    public void setPriKey(byte[] priKey) {
        this.priKey = priKey;
    }

    public byte[] getCert() {
        return cert;
    }

    public void setCert(byte[] cert) {
        this.cert = cert;
    }

    public boolean isUsage() {
        return usage;
    }

    public void setUsage(boolean usage) {
        this.usage = usage;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    @Override
    public String toString() {
        return "KeyPairVo{" +
                "seqId=" + seqId +
                ", priKey=" + Arrays.toString(priKey) +
                ", cert=" + Arrays.toString(cert) +
                ", usage=" + usage +
                ", kid='" + kid + '\'' +
                '}';
    }
}

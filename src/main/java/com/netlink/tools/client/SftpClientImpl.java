package com.netlink.tools.client;

import java.io.*;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.springframework.util.StringUtils;

/**
 * SftpClientImpl
 *
 * @author fubencheng.
 * @version 0.0.1 2018-03-24 13:01 fubencheng.
 */
@Slf4j
public class SftpClientImpl implements SftpClient {

	private static final int DEFAULT_TIMEOUT = 5000;

	private static final int DEFAULT_PORT = 22;

	private String host;

	private int port = DEFAULT_PORT;

	private String userName;

	private String password;

	private int timeout = DEFAULT_TIMEOUT;

	private Session session;
	private Channel channel;

	private ChannelSftp getChannel() {

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(userName, host, port);

			Properties config = new Properties();
			// 不检查Host Key
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(password);
			session.setTimeout(timeout);
			session.connect();

			// 打开SFTP通道
			channel = session.openChannel("sftp");
			channel.connect();

			return (ChannelSftp) channel;
		} catch (Exception e) {
			throw new RuntimeException("Open sftp channel failed, host=" + host + ", port=" + port + ", userName=" + userName);
		}
	}

	@Override
	public void uploadFile(String src, String dir, String fileName, int mode) {
		log.info("upload file, src[{}], dir[{}], fileName[{}], mode[{}]", src, dir, fileName, mode);
		ChannelSftp channel = getChannel();
		changeDir(channel, dir);

		if (StringUtils.isEmpty(fileName)) {
			// 上传到目录
			fileName = dir;
		}

		try {
			channel.put(src, fileName, mode);
		} catch (SftpException e) {
			log.error("upload file failed, src={}, fileName={}, dir={}", src, fileName, dir);
		} finally {
			channel.quit();
			closeChannel();
		}
	}

	@Override
	public void uploadFile(InputStream inStream, String dir, String fileName, int mode) {
		ChannelSftp channel = getChannel();
		changeDir(channel, dir);

		if (StringUtils.isEmpty(fileName)) {
			log.error("file name cannot be null");
			return;
		}

		// 设定每次传输的数据块大小为 8KB
		byte[] buff = new byte[1024 * 8];
		int readNum;
		try(OutputStream outStream = channel.put(fileName, mode)) {
			while ((readNum = inStream.read(buff)) != -1) {
				outStream.write(buff, 0, readNum);
			}
			outStream.flush();
		} catch (Exception e) {
			log.error("upload file failed, fileName={}, dir={}", fileName, dir);
		} finally {
			channel.quit();
			closeChannel();
		}
	}

	@Override
	public void downloadFile(String remotePath, String fileName, String dest, int mode) {
		ChannelSftp channel = getChannel();
		changeDir(channel, remotePath);

		if (StringUtils.isEmpty(fileName)) {
			log.error("download file name cannot be null, remotePath={}, fileName={}, dest={}", remotePath, fileName, dest);
			return;
		}

		try(OutputStream outStream = new FileOutputStream(dest);
			InputStream inStream = channel.get(fileName)) {
			if (new File(dest).isDirectory()) {
				// 下载到本地目录
				channel.get(fileName, dest);
				return;
			}

			byte[] buff = new byte[1024 * 8];
			int readNum;
			while ((readNum = inStream.read(buff)) != -1) {
				outStream.write(buff, 0, readNum);
			}
			outStream.flush();
		} catch (Exception e) {
			log.error("download file failed, remotePath={}, fileName={}, dest={}", remotePath, fileName, dest);
		} finally {
			channel.quit();
			closeChannel();
		}
	}

	@Override
	public InputStream downloadFile(String remotePath, String fileName, int mode) {
		ChannelSftp channel = getChannel();
		changeDir(channel, remotePath);

		if (StringUtils.isEmpty(fileName)) {
			throw new RuntimeException("download file name cannot be null");
		}

		try {
			return channel.get(fileName);
		} catch (Exception e) {
			throw new RuntimeException("download file failed, remotePath=" + remotePath + ", fileName=" + fileName);
		} finally {
			channel.quit();
			closeChannel();
		}
	}

	@Override
	public void renameFile(String remotePath, String originalName, String newName) {
		ChannelSftp channel = getChannel();
		changeDir(channel, remotePath);

		if (StringUtils.isEmpty(originalName) || StringUtils.isEmpty(newName)) {
			log.error("file name cannot be null, remotePath={}, originalName={}, newName={}", remotePath, originalName, newName);
			return;
		}

		try {
			channel.rename(originalName, newName);
		} catch (Exception e) {
			log.error("rename file failed, remotePath={}, originalName={}, newName={}", remotePath, originalName, newName);
		} finally {
			channel.quit();
			closeChannel();
		}
	}

	private void changeDir(ChannelSftp channel, String dir) {
		try {
			// 尝试进入目录
			channel.cd(dir);
		} catch (Exception e) {
			try {
				// 创建目录
				channel.mkdir(dir);
				channel.cd(dir);
			} catch (SftpException se) {
				throw new RuntimeException("cannot make dir, dir=" + dir);
			}
		}
	}

	private void closeChannel() {
		if (session != null) {
			session.disconnect();
		}
		if (channel != null) {
			channel.disconnect();
		}
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}

package com.netlink.tools.client;

import java.io.*;
import java.util.Properties;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.springframework.util.StringUtils;

/**
 * SftpClient
 *
 * @author fubencheng.
 * @version 0.0.1 2018-03-24 13:01 fubencheng.
 */
@Slf4j
public final class SftpClient {

	private static final int DEFAULT_TIMEOUT = 5000;
	private static final int DEFAULT_PORT = 22;
	private static final String DEFAULT_HOST = "127.0.0.1";

	@Setter
	private String host = DEFAULT_HOST;

	@Setter
	private int port = DEFAULT_PORT;

	@Setter
	private String userName;

	@Setter
	private String password;

	@Setter
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

	/**
	 * 将本地绝对全路径的文件上传到指定的目录或者指定名称的文件
	 *
	 * @param src 源文件全路径
	 * @param dir 目标目录
	 * @param fileName 目标文件名
	 * @param mode 上传模式
	 */
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

	/**
	 * 将输入流写到目标文件中，目标文件不能是目录
	 *
	 * @param inStream inStream
	 * @param dir dir
	 * @param fileName fileName
	 * @param mode mode
	 */
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

	/**
	 * 下载文件到本地目录或者文件
	 *
	 * @param remotePath remotePath
	 * @param fileName fileName
	 * @param dest dest
	 * @param mode mode
	 */
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

	/**
	 * 下载文件，返回输入流
	 *
	 * @param remotePath remotePath
	 * @param fileName fileName
	 * @return InputStream
	 */
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

	/**
	 * 重命名文件
	 *
	 * @param remotePath remotePath
	 * @param originalName originalName
	 * @param newName newName
	 */
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

}
